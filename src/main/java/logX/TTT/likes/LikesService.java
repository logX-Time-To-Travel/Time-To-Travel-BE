package logX.TTT.likes;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import logX.TTT.post.model.PostSummaryDTO;
import logX.TTT.views.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final ViewsRepository viewsRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository, ViewsRepository viewsRepository) {
        this.likesRepository = likesRepository;
        this.viewsRepository = viewsRepository;
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

    // 사용자가 좋아요한 게시물 목록 반환
    public List<PostSummaryDTO> getLikedPostsByMember(Member member) {
        List<Likes> likedLikes = likesRepository.findByMember(member); // 사용자가 좋아요한 Likes 목록 조회

        return likedLikes.stream().map(like -> {
            Post likedPost = like.getPost();
            int likeCount = likesRepository.findByPost(likedPost).size(); // 해당 게시물의 좋아요 수
            int viewCount = viewsRepository.findByPost(likedPost).size(); // 해당 게시물의 조회수

            // PostSummaryDTO 생성
            return new PostSummaryDTO(
                    likedPost.getId(),
                    likedPost.getTitle(),
                    likeCount,
                    viewCount,
                    likedPost.getImageUrl(),
                    likedPost.getCreatedAt()
            );
        }).collect(Collectors.toList());
    }
}
