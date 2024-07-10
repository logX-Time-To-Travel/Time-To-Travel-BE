package logX.TTT.member;

import logX.TTT.member.model.LoginDTO;
import logX.TTT.member.model.SignupDTO;
import logX.TTT.member.model.UpdateMemberDTO;
import logX.TTT.member.model.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member login(LoginDTO form) {
        Optional<Member> optionalMember = memberRepository.findByEmail(form.getEmail());
        Member member = optionalMember.orElseThrow(() -> new RuntimeException("가입된 이메일이 없습니다."));

        if (!passwordEncoder.matches(form.getPassword(), member.getPassword())) { // 비밀번호 대조
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }


    public Member signup(SignupDTO form) {
        if(memberRepository.existsByEmail(form.getEmail())) {
            throw new RuntimeException("해당 이메일로 가입된 회원이 있습니다.");
        }
        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setEmail(form.getEmail());
        member.setPassword(passwordEncoder.encode(form.getPassword()));
        member.setProfileImageUrl(form.getProfileImageUrl());
        return memberRepository.save(member);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원이 없습니다."));
    }

    public UserInfoDTO convertToUserInfoDTO(Member member) {
        return new UserInfoDTO(
                member.getEmail(),
                member.getUsername(),
                member.getProfileImageUrl(),
                member.getCreatedAt()
        );
    }

    public boolean isEmailUsed(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isUsernameUsed(String username) {
        return memberRepository.existsByUsername(username);
    }

    public Member updateMember(UpdateMemberDTO form) {
        Optional<Member> optionalMember = memberRepository.findByUsername(form.getUsername());
        Member member = optionalMember.orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없음"));
        member.setUsername(form.getUsername());
        member.setProfileImageUrl(form.getProfileImageUrl());
        return memberRepository.save(member);
    }

    public void delete(String username) {
        memberRepository.deleteByUsername(username);
    }
}
