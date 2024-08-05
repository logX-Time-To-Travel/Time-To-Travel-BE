package logX.TTT.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private Long postId;
    private String username;
    private String profileImageUrl;
    private String content;
    private LocalDateTime createdAt;
}
