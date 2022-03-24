package donglee99.instagram.web.service;

import donglee99.instagram.web.domain.Post;
import donglee99.instagram.web.domain.User;
import donglee99.instagram.web.dto.PostDto;
import donglee99.instagram.web.dto.PostInfoDto;
import donglee99.instagram.web.dto.PostUpdateDto;
import donglee99.instagram.web.dto.PostUploadDto;
import donglee99.instagram.web.repository.PostRepository;
import donglee99.instagram.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Value("${post.path")
    private String uploadUrl;

    @Transactional
    public void save(PostUploadDto postUploadDto, long id, MultipartFile multipartFile) {
        UUID uuid = UUID.randomUUID();
        String imgFileName = uuid + "_" + multipartFile.getOriginalFilename();

        Path imageFilePath = Paths.get(uploadUrl + imgFileName);
        try {
            Files.write(imageFilePath, multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("응애"));
        postRepository.save(Post.builder()
                .postImgUrl(imgFileName)
                .tag(postUploadDto.getTag())
                .text(postUploadDto.getText())
        .user(user)
        .build());
    }

    @Transactional
    public void update(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException());
        post.update(postUpdateDto.getTag(), postUpdateDto.getText());
    }

    public PostInfoDto getPostInfoDto(long postId, String name) {
        return null;
    }

    public PostDto getPostDto(long postId) {
        return null;
    }

    @Transactional
    public void delete(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException());

        File file = new File(uploadUrl + post.getPostImgUrl());
        file.delete();
        postRepository.deleteById(postId);
    }
}
