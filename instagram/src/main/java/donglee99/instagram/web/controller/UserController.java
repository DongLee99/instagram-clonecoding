package donglee99.instagram.web.controller;

import donglee99.instagram.web.dto.UserDto;
import donglee99.instagram.web.dto.UserUpdateDto;
import donglee99.instagram.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/update")
    public String update(Authentication authentication, Model model) {
        UserDto userDto = userService.getUserDtoByEmail(authentication.getName());
        model.addAttribute("userDto", userDto);
        return "user/update";
    }

    @PutMapping("/update")
    public String update(UserUpdateDto userUpdateDto, RedirectAttributes redirectAttributes) {
        userService.update(userUpdateDto);
        redirectAttributes.addAttribute("id", userUpdateDto.getId());
        return "redirect:/user/profile";
    }

}
