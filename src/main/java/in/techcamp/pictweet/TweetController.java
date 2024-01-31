package in.techcamp.pictweet;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.boot.Banner;


@Controller
@AllArgsConstructor
//@RequiredArgsConstructor
public class TweetController {
    private final TweetRepository tweetRepository;


    @GetMapping
    public String showTweet(Model model) {
        var tweetList = tweetRepository.findAll();
        model.addAttribute("tweetList", tweetList);
        return "index";
    }

    @GetMapping("/tweetForm")
    public String showTweetForm(@ModelAttribute("tweetForm") TweetForm form) {
        return "tweetForm";
    }

    @PostMapping("/tweets")
    public String saveTweet(TweetForm form,
                            Model model) {
        try {
            tweetRepository.insert(form.getName(), form.getContent(), form.getImage());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        return "redirect:/";
    }

//                            ){
//        tweetRepository.insert(form.getName(),form.getContent(),form.getImage());
//        return "redirect:/";
//    }

}


