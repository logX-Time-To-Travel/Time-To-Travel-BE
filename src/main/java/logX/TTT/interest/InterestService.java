package logX.TTT.interest;

import logX.TTT.likes.LikesRepository;
import logX.TTT.member.MemberService;
import logX.TTT.post.Post;
import logX.TTT.post.PostRepository;
import logX.TTT.post.PostService;
import logX.TTT.post.model.PostInterestDTO;
import logX.TTT.post.model.PostResponseDTO;
import logX.TTT.post.model.PostSummaryDTO;
import logX.TTT.scrap.ScrapRepository;
import logX.TTT.search.SearchService;
import logX.TTT.search.model.SearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private ScrapRepository scrapRepository;

    public List<PostInterestDTO> getRecommendedPosts(String username) {
        Long memberId = memberService.getMemberIdByUsername(username);

        // 최근 검색 기록 기반 추천
        List<SearchDTO> recentQueries = searchService.getRecentSearchQueries(memberId);
        Set<Post> recommendedPosts = new HashSet<>();
        for (SearchDTO searchDTO : recentQueries) {
            recommendedPosts.addAll(postRepository.findByTitleContainingOrContentDataContaining(searchDTO.getQuery()));
        }

        // 좋아요한 게시물 기반 추천
        List<Post> likedPosts = likesRepository.findPostsByMemberId(memberId);
        for (Post likedPost : likedPosts) {
            recommendedPosts.addAll(postRepository.findByTitleContainingOrContentDataContaining(likedPost.getTitle()));
        }

        // 스크랩한 게시물 기반 추천
        List<Post> scrappedPosts = scrapRepository.findPostsByMemberId(memberId);
        for (Post scrappedPost : scrappedPosts) {
            recommendedPosts.addAll(postRepository.findByTitleContainingOrContentDataContaining(scrappedPost.getTitle()));
        }

        // 랜덤으로 5개 선택
        List<Post> randomPosts = recommendedPosts.stream().collect(Collectors.toList());
        Collections.shuffle(randomPosts);
        List<Post> selectedPosts = randomPosts.stream().limit(5).collect(Collectors.toList());

        return postService.convertToInterestDTOs(selectedPosts);
    }
}