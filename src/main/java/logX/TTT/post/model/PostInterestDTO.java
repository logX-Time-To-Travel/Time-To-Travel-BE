package logX.TTT.post.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostInterestDTO {
    private Long postId;
    private String thumbnail;
    private String profileImageUrl;
    private String username;
    private String title;
    private LocalDateTime createdAt;
}