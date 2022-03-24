package donglee99.instagram.web.controller;

import donglee99.instagram.web.dto.PostDto;
import donglee99.instagram.web.dto.PostUploadDto;
import donglee99.instagram.web.dto.UserDto;
import donglee99.instagram.web.service.PostService;
import donglee99.instagram.web.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Getter
@Controller
public class PostController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/post/upload/{postId}")
    public String upload(@PathVariable long postId, Authentication authentication, Model model) {
        PostDto postDto = postService.getPostDto(postId);
        UserDto userDto = userService.getUserDtoByEmail(authentication.getName());
        model.addAttribute("userDto", userDto);
        model.addAttribute("postDto", postDto);
        return "post/upload";
    }

    @PostMapping("post")
    public String uploadPost(PostUploadDto postUploadDto, @RequestParam("uploadImgUrl")MultipartFile multipartFile, RedirectAttributes redirectAttributes, Authentication authentication) {
        long id  = userService.getUserIdByEmail(authentication.getName());
        postService.save(postUploadDto, id, multipartFile);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/iser/profile";
    }

    @DeleteMapping("/post/delete")
    public String delete(@RequestParam("postId") long postId, Authentication authentication, RedirectAttributes redirectAttributes) {
        long id = userService.getUserIdByEmail(authentication.getName());
        postService.delete(postId);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/user/profile";
    }

}
