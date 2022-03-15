package donglee99.instagram.web.dto;

import donglee99.instagram.web.domain.User;
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

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.phone = user.getPhoneNumber();
        this.name = user.getName();
        this.title = user.getTitle();
        this.website = user.getWebsite();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}
