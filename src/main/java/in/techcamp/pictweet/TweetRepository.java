package in.techcamp.pictweet;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TweetRepository extends JpaRepository<TweetEntity, Integer> {
    // ユーザーIDに基づいて投稿を取得するメソッド
    List<TweetEntity> findByUserId(Integer userId);

    List<TweetEntity> findByContentContaining(String keyword);
}
