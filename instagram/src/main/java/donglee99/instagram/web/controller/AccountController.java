package donglee99.instagram.web.controller;

import donglee99.instagram.web.dto.UserLoginDto;
import donglee99.instagram.web.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(UserLoginDto userLoginDto) {
        if (userService.save(userLoginDto)) {
            return "redirect:/login";
        } else {
            return "redirect:/signup?error";
        }
    }
}
