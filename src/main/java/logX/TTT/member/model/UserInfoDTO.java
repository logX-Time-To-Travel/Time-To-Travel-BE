package logX.TTT.member.model;

import logX.TTT.post.model.PostSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String email;
    private String username;
    private String introduction;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    private int totalLikeCount;
    private int totalViewCount;
    private List<PostSummaryDTO> myPosts;
    private int totalPostCount;
    private int totalCommentCount;

}
