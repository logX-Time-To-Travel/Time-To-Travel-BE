package logX.TTT.views;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewsService {

    private final ViewsRepository viewsRepository;

    @Autowired
    public ViewsService(ViewsRepository viewsRepository) {
        this.viewsRepository = viewsRepository;
    }

    // 조회수 추가
    public Views addView(Post post, Member member) {
        Views view = Views.builder()
                .post(post)
                .member(member)
                .build();
        return viewsRepository.save(view);
    }

    // 특정 게시물에 대한 조회수 목록 조회
    public List<Views> getViewsByPost(Post post) {
        return viewsRepository.findByPost(post);
    }

    // 특정 사용자가 조회한 목록 조회
    public List<Views> getViewsByMember(Member member) {
        return viewsRepository.findByMember(member);
    }
}
