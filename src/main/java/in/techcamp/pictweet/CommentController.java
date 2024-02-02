package in.techcamp.pictweet;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("tweet/{tweetId}/comment")
    public String tweetComment(Authentication authentication,
                              @ModelAttribute CommentEntity commentEntity,
                              @PathVariable("tweetId") Integer tweetId,
                              Model model )
    {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);
        TweetEntity tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new EntityNotFoundException("Tweet not found: " + tweetId));

        commentEntity.setUser(user);
        commentEntity.setTweet(tweet);

        try {
            commentRepository.save(commentEntity);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }

        return "redirect:/tweet/" + tweetId;
    }
}