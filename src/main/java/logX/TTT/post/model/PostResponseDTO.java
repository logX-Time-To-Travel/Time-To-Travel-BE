package logX.TTT.post.model;

import logX.TTT.likes.Likes;
import logX.TTT.location.model.LocationDTO;
import logX.TTT.views.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String title;
    private String username;
    private String profileImageUrl;
    private String introduction;
    private String data;
    private List<LocationDTO> locations;
    private int postCount;
    private int likeCount;
    private int viewCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private boolean liked;
}