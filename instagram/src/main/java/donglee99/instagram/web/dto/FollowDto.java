package donglee99.instagram.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FollowDto {

    private long id;
    private String name;
    private String profileImgUrl;
    private int followState;
    private int loginUser;


    public FollowDto(long id, String name, String profileImgUrl, int followState, int loginUser) {
        this.id = id;
        this.name = name;
        this.profileImgUrl = profileImgUrl;
        this.followState = followState;
        this.loginUser = loginUser;
    }
}
