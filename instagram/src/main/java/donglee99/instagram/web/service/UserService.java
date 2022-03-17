package donglee99.instagram.web.service;

import donglee99.instagram.web.domain.User;
import donglee99.instagram.web.dto.UserDto;
import donglee99.instagram.web.dto.UserLoginDto;
import donglee99.instagram.web.dto.UserProfileDto;
import donglee99.instagram.web.dto.UserUpdateDto;
import donglee99.instagram.web.repository.FollowRepository;
import donglee99.instagram.web.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Value("${profileImg.path")
    private String uploadFolder;

    @Transactional
    public boolean save(UserLoginDto userLoginDto) {
        if (userRepository.existsByEmail(userLoginDto.getEmail())) {
            throw new DuplicateKeyException("중복");
        }
        userRepository.save(userLoginDto.toEntity());
        return true;
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto, MultipartFile multipartFile) {
        User user = userRepository.findByEmail(userUpdateDto.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String imageFileName = user.getId() + "_" + multipartFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        if (multipartFile.getSize() != 0) {
            try {
                if (user.getProfileImgUrl() != null) {
                    File file = new File(uploadFolder + user.getProfileImgUrl());
                    file.delete();
                }
                Files.write(imageFilePath, multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setProfileImgUrl(imageFileName);
        }
        user.update(encoder.encode(userUpdateDto.getPassword()), userUpdateDto.getPhone(), userUpdateDto.getName(), userUpdateDto.getTitle(), userUpdateDto.getWebsite(), userUpdateDto.getProfileImgUrl());
    }

    @Transactional
    public UserProfileDto getProfile(long currentId, String loginEmail) {

        User user = userRepository.getById(currentId);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUserDto(new UserDto(user));

        User loginUser = userRepository.findByEmail(loginEmail);
        userProfileDto.setLoginUser(loginUser.getId() == user.getId());
        userProfileDto.setLoginId(loginUser.getId());

        userProfileDto.setFollow(followRepository.findByfromUserAndtoUser(loginUser, user) != null);

        userProfileDto.setUserFollowerCount(followRepository.findFollwerCountById(currentId));
        userProfileDto.setUserFollowingCount(followRepository.findFollowingCountBy(currentId));
        return userProfileDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) return null;
        return user;
    }

    public UserDto getUserDtoByEmail(String name) {
        User byEmail = userRepository.findByEmail(name);
        UserDto userDto = new UserDto(byEmail);
        return userDto;
    }
}
