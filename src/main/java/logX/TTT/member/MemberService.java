package logX.TTT.member;

import logX.TTT.likes.LikesRepository;
import logX.TTT.member.model.LoginDTO;
import logX.TTT.member.model.SignupDTO;
import logX.TTT.member.model.UpdateMemberDTO;
import logX.TTT.member.model.UserInfoDTO;
import logX.TTT.post.Post;
import logX.TTT.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;


    public Member login(LoginDTO form) {
        Optional<Member> optionalMember = memberRepository.findByEmail(form.getEmail());
        Member member = optionalMember.orElseThrow(() -> new RuntimeException("가입된 이메일이 없습니다."));

        if (!passwordEncoder.matches(form.getPassword(), member.getPassword())) { // 비밀번호 대조
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }


    public Member signup(SignupDTO form) {
        if (memberRepository.existsByEmail(form.getEmail())) {
            throw new RuntimeException("해당 이메일로 가입된 회원이 있습니다.");
        }

        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setEmail(form.getEmail());
        member.setPassword(passwordEncoder.encode(form.getPassword()));
        member.setProfileImageUrl(form.getProfileImageUrl());
        member.setIntroduction("한 줄 소개입니다. 자신을 멋있게 소개해보세요!");
        member.setTotalLikeCount(0);
        member.setTotalViewCount(0);

        return memberRepository.save(member);
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 회원이 없습니다."));
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원이 없습니다."));
    }

    public UserInfoDTO convertToUserInfoDTO(Member member) {
        return new UserInfoDTO(
                member.getEmail(),
                member.getUsername(),
                member.getIntroduction(),
                member.getProfileImageUrl(),
                member.getCreatedAt(),
                member.getTotalLikeCount(),
                member.getTotalViewCount(),
                member.getMyPosts(),
                member.getTotalPostCount(),
                member.getTotalCommentCount()
        );
    }

    public boolean isUsernameUsed(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional
    public Member updateMember(String username, UpdateMemberDTO updateMemberDTO) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        member.setUsername(updateMemberDTO.getUsername());
        member.setIntroduction(updateMemberDTO.getIntroduction());
        member.setProfileImageUrl(updateMemberDTO.getProfileImageUrl());

        return memberRepository.save(member);
    }

    @Transactional
    public void delete(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

    public Long getMemberIdByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return member.getId();
    }

    public UserInfoDTO getUserInfoByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return new UserInfoDTO(
                member.getEmail(),
                member.getUsername(),
                member.getIntroduction(),
                member.getProfileImageUrl(),
                member.getCreatedAt(),
                member.getTotalLikeCount(),
                member.getTotalViewCount(),
                member.getMyPosts(),
                member.getTotalPostCount(),
                member.getTotalCommentCount()
        );
    }

    // Member에 해당하는 게시물 목록을 반환
    public List<Post> getPostsByMember(Member member) {
        return postRepository.findByMember(member);
    }
}
