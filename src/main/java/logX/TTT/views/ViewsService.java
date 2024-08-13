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

    // 한 사용자가 작성한 모든 게시물의 총 조회수 가져오기
    public int getTotalPostViewsByMember(Member member) {
        List<Post> posts = member.getPosts(); // Member의 게시물 목록 가져오기

        // 모든 게시물의 조회수를 합산
        int totalViewCount = posts.stream()
                .mapToInt(post -> viewsRepository.findByPost(post).size())
                .sum();

        return totalViewCount; // 총 조회수 반환
    }
}
