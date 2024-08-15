package logX.TTT.post;

import logX.TTT.post.model.PostCreateDTO;
import logX.TTT.post.model.PostResponseDTO;
import logX.TTT.post.model.PostSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/add")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostCreateDTO postCreateDTO) {
        try {
            PostResponseDTO createdPost = postService.createPost(postCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long id) {
        try {
            PostResponseDTO postResponseDTO = postService.getPost(id);
            return ResponseEntity.ok(postResponseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @RequestBody PostCreateDTO postCreateDTO) {
        try {
            PostResponseDTO updatedPost = postService.updatePost(id, postCreateDTO);
            return ResponseEntity.ok(updatedPost);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 하나의 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 여러 개의 게시물 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMultiplePosts(@RequestBody List<Long> ids) {
        try {
            for (Long id : ids) {
                postService.deletePost(id);
            }
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostSummaryDTO>> getPostsByUsername(@PathVariable String username) {
        try {
            List<PostSummaryDTO> posts = postService.getPostsByUsername(username);
            return ResponseEntity.ok(posts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
