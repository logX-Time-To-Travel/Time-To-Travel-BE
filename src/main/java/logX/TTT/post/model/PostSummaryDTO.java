package logX.TTT.post.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDTO {
    private Long postId;
    private String title;
    private int likeCount;
    private int viewCount;
    private String imageUrl;
}
