package in.techcamp.pictweet;

import jakarta.persistence.*;
import lombok.Data;


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
}