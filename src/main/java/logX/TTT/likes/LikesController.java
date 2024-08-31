package logX.TTT.likes;

import logX.TTT.member.Member;
import logX.TTT.member.MemberService;
import logX.TTT.post.model.PostSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;
    private final MemberService memberService;

    @PostMapping("/{postId}/{memberId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId, @PathVariable Long memberId) {
        try {
            likesService.likePost(postId, memberId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{postId}/{memberId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId, @PathVariable Long memberId) {
        try {
            likesService.unlikePost(postId, memberId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<PostSummaryDTO>> getLikePosts(@PathVariable Long memberId) {
        try {
            Member member = memberService.getMemberById(memberId);
            List<PostSummaryDTO> posts = likesService.getLikedPostsByMember(member);
            return ResponseEntity.ok(posts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build();
        }
    }
}