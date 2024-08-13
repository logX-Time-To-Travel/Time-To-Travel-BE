package logX.TTT.post.model;


import logX.TTT.location.model.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {
    private Long memberId;
    private String title;
    private String content;
    private String thumbnail;
    private List<LocationDTO> locations;
}
