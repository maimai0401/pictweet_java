package in.techcamp.pictweet;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TweetController {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public String showTweet(Model model) {
        List<TweetEntity> tweetList = tweetRepository.findAll();
        model.addAttribute("tweetList", tweetList);
        return "index";
    }

    @GetMapping("/tweetForm")
    public String showTweetForm(@ModelAttribute("tweetForm") TweetEntity tweetEntity) {
        return "tweetForm";
    }

    @PostMapping("/tweets")
    public String saveTweet(@ModelAttribute("tweetForm") TweetEntity tweetEntity, Authentication authentication,
                            Model model) {
        User authenticatedUser = (User) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        UserEntity user = userRepository.findByUsername(username);
        tweetEntity.setUser(user);
        try {
            tweetRepository.save(tweetEntity);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        return "redirect:/";
    }

    @GetMapping("/tweet/{tweetId}")
    public String showTweetDetail(@PathVariable("tweetId") Integer tweetId,
                                  @ModelAttribute("commentEntity") CommentEntity commentEntity,

                                  Model model) {
        TweetEntity tweet;

        try {
            tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new EntityNotFoundException("tweet not found: " + tweetId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
        List<CommentEntity> comments = commentRepository.findByTweet_id(tweetId);
        model.addAttribute("comments", comments);
        model.addAttribute("tweet", tweet);

        return "detail";
    }

    @GetMapping("/user/{userId}/tweet/{tweetId}/edit")
    public String edit(@PathVariable("tweetId") Integer tweetId, Model model) {

        TweetEntity tweet;

        try {
            tweet = tweetRepository.findById(tweetId)
                    .orElseThrow(() -> new EntityNotFoundException("Tweet not found: " + tweetId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        model.addAttribute("tweet", tweet);

        return "edit";
    }

    @PostMapping("/user/{userId}/tweet/{tweetId}/update")
    public String update(Authentication authentication,
                         @ModelAttribute("tweet") TweetEntity tweetEntity,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("tweetId") Integer tweetId,
                         Model model
    ) {

        String username = authentication.getName();

        TweetEntity tweet;
        UserEntity user;

        try {
            tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new EntityNotFoundException("Tweet not found: " + tweetId));
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        if (user.getUsername().equals(username)) {
            try {
                tweet.setName(tweetEntity.getName());
                tweet.setContent(tweetEntity.getContent());

                tweetRepository.save(tweet);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "イシューの投稿者と一致しません。");
            return "error";
        }
        return "redirect:/tweet/" + tweetId;
    }

    @PostMapping("/user/{userId}/tweet/{tweetId}/delete")
    public String delete(Authentication authentication,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("tweetId") Integer tweetId,
                         Model model) {

        String username = authentication.getName();

        UserEntity user;

        try {
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        if (user.getUsername().equals(username)) {
            try {
                tweetRepository.deleteById(tweetId);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "イシューの投稿者と一致しません。");
            return "error";
        }
        return "redirect:/";
    }
}


