package in.techcamp.pictweet;


import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private TweetEntity tweet;

}