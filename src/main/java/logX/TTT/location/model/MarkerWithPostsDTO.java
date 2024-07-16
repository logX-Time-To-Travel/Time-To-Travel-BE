package logX.TTT.location.model;

import logX.TTT.post.model.PostSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerWithPostsDTO {
    private String name;
    private double latitude;
    private double longitude;
    private List<PostSummaryDTO> posts;
}
