package in.techcamp.pictweet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<TweetEntity, Integer> {

}
