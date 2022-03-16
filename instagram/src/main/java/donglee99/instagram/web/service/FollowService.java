package donglee99.instagram.web.service;

import donglee99.instagram.web.domain.Follow;
import donglee99.instagram.web.domain.User;
import donglee99.instagram.web.repository.FollowRepository;
import donglee99.instagram.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

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
}
