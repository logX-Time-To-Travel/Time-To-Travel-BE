package logX.TTT.interest;

import logX.TTT.post.Post;
import logX.TTT.post.model.PostResponseDTO;
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
    public ResponseEntity<List<PostResponseDTO>> getRecommendedPosts(@PathVariable String username) {
        List<PostResponseDTO> recommendedPosts = interestService.getRecommendedPosts(username);
        return ResponseEntity.ok(recommendedPosts);
    }
}
