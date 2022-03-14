package donglee99.instagram.web.dto;

import donglee99.instagram.web.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    public User toEntity() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return User.builder()
                .email(email)
                .password(encoder.encode(password))
                .phoneNumber(phoneNumber)
                .name(name).build();
    }
}
