package logX.TTT.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import logX.TTT.likes.LikesService;
import logX.TTT.member.model.*;
import logX.TTT.post.model.PostSummaryDTO;
import logX.TTT.views.ViewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LikesService likesService;
    private final ViewsService viewsService;

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
    public ResponseEntity<SessionDTO> session(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("member") == null) {
            return ResponseEntity.status(401).build();
        }
        Long loggedInId = (Long) session.getAttribute("member");
        Member member = memberService.getMemberById(loggedInId);

        SessionDTO form = new SessionDTO(member.getId(), member.getUsername());
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

    @PutMapping("/{username}")
    public ResponseEntity updateUserInfo(@PathVariable String username, @RequestBody UpdateMemberDTO updateMemberDTO) {
        try {
            memberService.updateMember(username, updateMemberDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/{username}/posts/summary") // 엔드포인트 수정할 예정
    public ResponseEntity<List<PostSummaryDTO>> getPostSummary(@PathVariable String username) {
        Member member = memberService.getMemberByUsername(username); // 사용자를 username으로 조회
        if (member == null) {
            return ResponseEntity.status(404).body(null); // 사용자 없음
        }

        List<PostSummaryDTO> likesSummary = likesService.getPostLikesByMember(member);
        List<PostSummaryDTO> viewsSummary = viewsService.getPostViewsByMember(member);

        // 조회수와 좋아요 수 통합
        for (PostSummaryDTO post : likesSummary) {
            viewsSummary.stream()
                    .filter(v -> v.getPostId().equals(post.getPostId()))
                    .findFirst()
                    .ifPresent(v -> post.setViewCount(v.getViewCount())); // 조회수 설정
        }

        return ResponseEntity.ok(likesSummary);
    }
}
