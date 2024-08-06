package logX.TTT.views;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import logX.TTT.post.model.PostSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewsService {

    private final ViewsRepository viewsRepository;

    @Autowired
    public ViewsService(ViewsRepository viewsRepository) {
        this.viewsRepository = viewsRepository;
    }

    // 한 사용자가 작성한 모든 게시물의 조회수 가져오기
    public List<PostSummaryDTO> getPostViewsByMember(Member member) {
        List<Post> posts = member.getPosts(); // Member의 게시물 목록 가져오기

        return posts.stream().map(post -> {
            int viewCount = (int) viewsRepository.findByPost(post).size();

            return new PostSummaryDTO(
                    post.getId(),
                    post.getTitle(),
                    0, // 좋아요 수는 0으로 설정 (나중에 LikesService와 통합)
                    viewCount,
                    post.getImageUrl(),
                    post.getCreatedAt() // 작성한 날짜
            );
        }).collect(Collectors.toList());
    }
}
