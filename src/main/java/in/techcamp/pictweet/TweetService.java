package in.techcamp.pictweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    // ユーザーIDに基づいてそのユーザーの投稿一覧を取得
    public List<TweetEntity> findTweetsByUserId(Integer userId) {
        return tweetRepository.findByUserId(userId);
    }
}