package logX.TTT.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    private String username;
    private String email;
    private String password;
    private String profileImageUrl;
}
