package donglee99.instagram.web.repository;

import donglee99.instagram.web.domain.Follow;
import donglee99.instagram.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByfromUserAndtoUser(User fromUser, User toUser);

    int findFollwerCountById(long currentId);

    int findFollowingCountBy(long currentId);
}
