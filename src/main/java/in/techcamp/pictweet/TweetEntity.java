package in.techcamp.pictweet;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class TweetEntity {
    private long id;
    private String name;
    private String content;
    private String image;
}