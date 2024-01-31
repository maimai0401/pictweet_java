package in.techcamp.pictweet;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Controller
//@AllArgsConstructor
//@RequiredArgsConstructor
public class TweetController {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;
//    private final TweetRepository tweetRepository;


    @GetMapping
    public String showTweet(Model model) {
        List<TweetEntity> tweetList = tweetRepository.findAll();
//        var tweetList = tweetRepository.findAll();
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
    public String showTweetDetail(@PathVariable("tweetId") Integer tweetId, Model model) {
        TweetEntity tweet;

        try {
            tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new EntityNotFoundException("tweet not found: " + tweetId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        model.addAttribute("tweet", tweet);

        return "detail";
    }
}


