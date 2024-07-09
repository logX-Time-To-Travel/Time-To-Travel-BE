package logX.TTT.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    private Member login(String username, String password) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if(optionalMember.isPresent()) { // 회원 조회
            Member member = optionalMember.get();
            if(passwordEncoder.matches(password, member.getPassword())) { // 비밀번호 대조
                return member;
            }
        }
        return null;
    }

    private Member signup(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    private boolean isEmailUsed(String email) {
        return memberRepository.existsByEmail(email);
    }

    private boolean isUsernameUsed(String username) {
        return memberRepository.existsByUsername(username);
    }

    private void delete(String username) {
        memberRepository.deleteByUsername(username);
    }
}
