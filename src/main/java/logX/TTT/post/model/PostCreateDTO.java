package logX.TTT.post.model;

import logX.TTT.content.model.ContentDTO;
import logX.TTT.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {
    private Long memberId;
    private String title;
    private String content;
    private List<Location> locations;
    private LocalDateTime createdAt;
}
