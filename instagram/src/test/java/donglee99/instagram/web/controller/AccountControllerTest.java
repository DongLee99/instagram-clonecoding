package donglee99.instagram.web.controller;

import donglee99.instagram.web.domain.User;
import donglee99.instagram.web.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    public void login_success() throws Exception {
        //given
        String username = "test@test";
        String password = "12345";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .name("username")
                .password(encoder.encode(password))
                .phoneNumber("123123123")
                .email(username)
                .build();
        userRepository.save(user);
        //when
        mvc.perform(formLogin("/loginform").user(username).password(password))
                .andDo(print())
                .andExpect(redirectedUrl("/login"));
        //then

    }

}