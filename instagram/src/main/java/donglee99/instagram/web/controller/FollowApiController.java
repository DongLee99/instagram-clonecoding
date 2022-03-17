package donglee99.instagram.web.controller;

import donglee99.instagram.web.domain.Follow;
import donglee99.instagram.web.dto.FollowDto;
import donglee99.instagram.web.repository.FollowRepository;
import donglee99.instagram.web.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FollowApiController {

    private final FollowRepository followRepository;
    private final FollowService followService;

    @PostMapping("/follow/{toUserId}")
    public Follow followUser(@PathVariable long toUserId, Authentication authentication) throws Exception {
        return followService.save(authentication.getName(), toUserId);
    }

    @DeleteMapping("/follow/{toUserId}")
    public void unFollowUser(@PathVariable long toUserId, Authentication authentication) throws Exception {
        followService.delete(toUserId, authentication.getName());
    }

    @GetMapping("/follow/{profileId}/follower")
    public List<FollowDto> getFollower(@PathVariable long profileId, Authentication authentication) {
        return followService.getFollowDtoListByProfileIdAboutFollower(profileId, authentication.getName());
    }
    @GetMapping("/follow/{profileId}/following")
    public List<FollowDto> getFollowing(@PathVariable long profileId, Authentication authentication) {
        return followService.getFollowDtoListByProfileIdAboutFollowing(profileId, authentication.getName());
    }

}
