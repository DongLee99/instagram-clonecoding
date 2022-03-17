package donglee99.instagram.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@Getter
@Setter
public class UserProfileDto {
    private long loginId;
    private boolean loginUser;
    private boolean follow;
    private UserDto userDto;
    private int userFollowerCount;
    private int userFollowingCount;


}
