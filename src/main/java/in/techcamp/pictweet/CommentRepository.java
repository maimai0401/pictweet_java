package in.techcamp.pictweet;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByTweet_id(Integer tweetId);

}