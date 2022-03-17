package donglee99.instagram.web.controller;

import donglee99.instagram.web.dto.UserDto;
import donglee99.instagram.web.dto.UserProfileDto;
import donglee99.instagram.web.dto.UserUpdateDto;
import donglee99.instagram.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
        //userService.update(userUpdateDto);
        redirectAttributes.addAttribute("id", userUpdateDto.getId());
        return "redirect:/user/profile";
    }

    @PutMapping("/user/update")
    public String updateUser(UserUpdateDto userUpdateDto, @RequestParam("profileImgUrl")MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        userService.update(userUpdateDto, multipartFile);
        redirectAttributes.addAttribute("id", userUpdateDto.getId());
        return "redirect:/user/profile";
    }

    @GetMapping("/user/profile")
    public String profile(Model model, @RequestParam Long id, Authentication authentication) {
        UserProfileDto userProfileDto = userService.getProfile(id, authentication.getName());
        model.addAttribute("userProfileDto", userProfileDto);
        return "user/profile";
    }

}
