package logX.TTT.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import logX.TTT.member.model.LoginDTO;
import logX.TTT.member.model.SignupDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO form, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            Member loginMember = memberService.login(form);
            HttpSession session = request.getSession();
            session.setAttribute("member", loginMember.getId());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupDTO form) {
        Map<String, String> response = new HashMap<>();
        try {
            memberService.signup(form);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }
}
