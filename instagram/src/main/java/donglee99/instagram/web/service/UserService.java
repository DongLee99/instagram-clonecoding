package donglee99.instagram.web.service;

import donglee99.instagram.web.domain.User;
import donglee99.instagram.web.dto.UserLoginDto;
import donglee99.instagram.web.dto.UserUpdateDto;
import donglee99.instagram.web.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public boolean save(UserLoginDto userLoginDto) {
        if (userRepository.existsByEmail(userLoginDto.getEmail())) {
            throw new DuplicateKeyException("중복");
        }
        userRepository.save(userLoginDto.toEntity());
        return true;
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto) {
        User user = userRepository.findByEmail(userUpdateDto.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.update(encoder.encode(userUpdateDto.getPassword()), userUpdateDto.getPhone(), userUpdateDto.getName(), userUpdateDto.getTitle(), userUpdateDto.getWebsite(), userUpdateDto.getProfileImgUrl());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) return null;
        return user;
    }
}
