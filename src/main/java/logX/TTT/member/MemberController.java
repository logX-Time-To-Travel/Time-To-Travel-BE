package logX.TTT.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import logX.TTT.comment.CommentService;
import logX.TTT.likes.LikesService;
import logX.TTT.member.model.*;
import logX.TTT.post.PostService;
import logX.TTT.post.model.PostSummaryDTO;
import logX.TTT.views.ViewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LikesService likesService;
    private final ViewsService viewsService;
    private final PostService postService;
    private final CommentService commentService;

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

    @GetMapping("/session")
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
        Member member = memberService.getMemberByUsername(username); // 사용자를 username으로 조회
        if (member == null) {
            return ResponseEntity.status(404).body(null); // 사용자 없음
        }

        // 사용자 정보 조회
        UserInfoDTO userInfo = memberService.getUserInfoByUsername(username);

        // 총 좋아요 수 및 총 조회수 계산
        int totalLikeCount = likesService.getTotalPostLikesByMember(member); // 총 좋아요 수
        int totalViewCount = viewsService.getTotalPostViewsByMember(member); // 총 조회수

        // 내가 쓴 글 목록 및 갯수
        List<PostSummaryDTO> myPosts = postService.getPostsByMember(); // 내가 쓴 글 목록
        int myPostCount = myPosts.size(); // 내가 쓴 글 갯수

        // 내가 쓴 댓글 갯수
        int myCommentCount = commentService.getCommentCountByMember(member); // 내가 쓴 댓글 갯수

        // 사용자 정보에 통합된 정보 설정
        userInfo.setTotalLikeCount(totalLikeCount);
        userInfo.setTotalViewCount(totalViewCount);
        userInfo.setMyPosts(myPosts);
        userInfo.setTotalPostCount(myPostCount);
        userInfo.setTotalCommentCount(myCommentCount);

        return ResponseEntity.ok(userInfo); // 사용자 정보와 통합된 정보를 반환
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
}
