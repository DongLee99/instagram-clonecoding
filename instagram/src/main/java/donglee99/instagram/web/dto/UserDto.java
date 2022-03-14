package donglee99.instagram.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class UserDto {

    private long id;
    private String email;
    private String phone;
    private String name;
    private String title;
    private String website;
    private String profileImgUrl;
}
