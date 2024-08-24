package logX.TTT.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/likes")
public class LikesController {

    @Autowired
    private LikesService likesService;

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
}