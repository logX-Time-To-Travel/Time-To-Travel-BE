package logX.TTT.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String email;
    private String username;
    private String introduction;
    private String profileImageUrl;
    private LocalDateTime created_at;
    private int totalLikeCount;
    private int totalViewCount;
}
