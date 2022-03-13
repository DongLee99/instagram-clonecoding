package donglee99.instagram.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
public class UserLoginDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String name;

    @Builder
    public UserLoginDto(String email, String password, String phoneNumber, String name) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}
