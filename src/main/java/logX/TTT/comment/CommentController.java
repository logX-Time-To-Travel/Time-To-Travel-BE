package logX.TTT.comment;


import logX.TTT.comment.model.CommentCreateDTO;
import logX.TTT.comment.model.CommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{postId}/add")
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentCreateDTO commentDTO) {
        try {
            CommentResponseDTO createdComment = commentService.createComment(commentDTO.getPostId(), commentDTO.getMemberId(), commentDTO.getContent());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 댓글 보기 (특정 포스트의 댓글 목록 조회)
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPost(@PathVariable Long postId) {
        try {
            List<CommentResponseDTO> comments = commentService.getCommentsByPost(postId);
            return ResponseEntity.ok(comments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long id, @RequestBody CommentCreateDTO commentDTO) {
        try {
            CommentResponseDTO updatedComment = commentService.updateComment(id, commentDTO.getContent());
            return ResponseEntity.ok(updatedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
