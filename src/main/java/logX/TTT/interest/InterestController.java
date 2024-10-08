package logX.TTT.interest;

import logX.TTT.post.Post;
import logX.TTT.post.model.PostInterestDTO;
import logX.TTT.post.model.PostResponseDTO;
import logX.TTT.post.model.PostSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/interest")
public class InterestController {
    @Autowired
    private InterestService interestService;

    @GetMapping("/{username}")
    public ResponseEntity<List<PostInterestDTO>> getRecommendedPosts(@PathVariable String username) {
        List<PostInterestDTO> recommendedPosts = interestService.getRecommendedPosts(username);
        return ResponseEntity.ok(recommendedPosts);
    }
}
