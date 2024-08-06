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

    // 한 사용자가 작성한 모든 게시물의 좋아요 수 가져오기
    public List<PostSummaryDTO> getPostLikesByMember(Member member) {
        List<Post> posts = member.getPosts(); // Member의 게시물 목록 가져오기

        return posts.stream().map(post -> {
            int likeCount = (int) likesRepository.findByPost(post).size();

            return new PostSummaryDTO(
                    post.getId(),
                    post.getTitle(),
                    likeCount,
                    0, // 조회수는 0으로 설정 (나중에 ViewsService와 통합)
                    post.getImageUrl(),
                    post.getCreatedAt() // 작성한 날짜
            );
        }).collect(Collectors.toList());
    }
}
