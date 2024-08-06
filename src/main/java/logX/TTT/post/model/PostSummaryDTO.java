package logX.TTT.post.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDTO {
    private Long postId;
    private String title;
    private int likeCount;
    private int viewCount;
    private String imageUrl;
    private LocalDateTime createdAt;
}
