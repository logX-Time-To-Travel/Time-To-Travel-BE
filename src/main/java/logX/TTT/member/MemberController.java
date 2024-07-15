package logX.TTT.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import logX.TTT.member.model.CheckUsernameDTO;
import logX.TTT.member.model.LoginDTO;
import logX.TTT.member.model.SignupDTO;
import logX.TTT.member.model.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO form, HttpServletRequest request) {
        try {
            Member loginMember = memberService.login(form);
            HttpSession session = request.getSession();
            session.setAttribute("member", loginMember.getId());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupDTO form) {
        try {
            memberService.signup(form);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/session")
    public ResponseEntity<UserInfoDTO> session(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("member") == null) {
            return ResponseEntity.status(401).build();
        }
        Long loggedInId = (Long) session.getAttribute("member");
        Member member = memberService.getMemberById(loggedInId);
        UserInfoDTO form = memberService.convertToUserInfoDTO(member);
        return ResponseEntity.ok(form);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) session.invalidate();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check-username")
    public ResponseEntity checkUsername(@RequestBody CheckUsernameDTO form) {
        if(memberService.isUsernameUsed(form.getUsername())) {
            return ResponseEntity.status(400).body("사용중인 닉네임입니다.");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity deleteMember(@PathVariable String username) {
        try {
            memberService.delete(username);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserInfoDTO> showUserInfo(@PathVariable String username) {
        UserInfoDTO userInfo = memberService.getUserInfoByUsername(username);
        return ResponseEntity.ok(userInfo);
    }
}
