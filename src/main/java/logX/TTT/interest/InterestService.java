package logX.TTT.interest;

import logX.TTT.member.MemberService;
import logX.TTT.post.Post;
import logX.TTT.post.PostRepository;
import logX.TTT.post.PostService;
import logX.TTT.post.model.PostResponseDTO;
import logX.TTT.search.SearchService;
import logX.TTT.search.model.SearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterestService {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private MemberService memberService;

    public List<PostResponseDTO> getRecommendedPosts(String username) {
        Long memberId = memberService.getMemberIdByUsername(username);
        List<SearchDTO> recentQueries = searchService.getRecentSearchQueries(memberId);
        List<Post> recommendedPosts = new ArrayList<>();
        for (SearchDTO searchDTO : recentQueries) {
            recommendedPosts.addAll(postRepository.findByTitleContainingOrContentDataContaining(searchDTO.getQuery()));
        }
        return postService.convertToResponseDTOs(recommendedPosts);
    }
}