package donglee99.instagram.web.service;

import donglee99.instagram.web.domain.Follow;
import donglee99.instagram.web.domain.User;
import donglee99.instagram.web.dto.FollowDto;
import donglee99.instagram.web.repository.FollowRepository;
import donglee99.instagram.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    @Transactional
    public Follow save(String name, long toUserId) throws Exception {
        User fromUser = userRepository.findByEmail(name);
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new Exception("찾을 수 없음"));
        return followRepository.save(Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser).build());
    }

    @Transactional
    public void delete(long toUserId, String name) throws Exception {
        User fromUser = userRepository.findByEmail(name);
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new Exception("찾을 수 없음"));

        Follow follow = followRepository.findByfromUserAndtoUser(fromUser, toUser);

    }

    public List<FollowDto> getFollowDtoListByProfileIdAboutFollower(long profileId, String loginEmail) {
        long loginId = userRepository.findByEmail(loginEmail).getId();

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.to_user_id AND f.from_user_id = ?");

        Query query = (Query) em.createNamedQuery(sb.toString())
                .setParameter(1, loginId)
                .setParameter(2, loginId)
                .setParameter(3, profileId);
        return null;
    }

    public List<FollowDto> getFollowDtoListByProfileIdAboutFollowing(long profileId, String name) {
        return null;
    }
}
