package logX.TTT.likes;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import logX.TTT.post.model.PostSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    // 한 사용자가 작성한 모든 게시물의 총 좋아요 수 가져오기
    public int getTotalPostLikesByMember(Member member) {
        List<Post> posts = member.getPosts(); // Member의 게시물 목록 가져오기

        // 모든 게시물의 좋아요 수를 합산
        int totalLikeCount = posts.stream()
                .mapToInt(post -> likesRepository.findByPost(post).size())
                .sum();

        return totalLikeCount; // 총 좋아요 수 반환
    }
}
