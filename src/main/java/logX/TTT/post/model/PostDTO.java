package logX.TTT.post.model;

import logX.TTT.content.model.ContentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private Long memberId;
    private String title;
    private List<ContentDTO> content;
    private LocalDateTime createdAt;
}
