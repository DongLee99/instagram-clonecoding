package donglee99.instagram.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String postImgUrl;
    private String tag;
    private String text;

    @ManyToOne
    private User user;

    private LocalDateTime createDate;

    @Builder
    public Post(String postImgUrl, String tag, String text, User user) {
        this.postImgUrl = postImgUrl;
        this.tag = tag;
        this.text = text;
        this.user = user;
    }

    @PrePersist
    public void CreateDate() {
        this.createDate = LocalDateTime.now();
    }

    public void update(String tag, String text) {
        this.tag = tag;
        this.text = text;
    }
}
