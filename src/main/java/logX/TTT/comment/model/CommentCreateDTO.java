package logX.TTT.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDTO {
    private Long memberId;
    private Long postId;
    private String content;
}