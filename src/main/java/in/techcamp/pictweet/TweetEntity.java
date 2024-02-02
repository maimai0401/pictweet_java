package in.techcamp.pictweet;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Data
@Table(name = "tweets")
@Entity
public class TweetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String content;
    private String image;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "tweet",cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;
}