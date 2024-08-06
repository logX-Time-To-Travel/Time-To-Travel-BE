package logX.TTT.likes;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    // 좋아요 추가
    public Likes addLike(Post post, Member member) {
        Likes like = Likes.builder()
                .post(post)
                .member(member)
                .build();
        return likesRepository.save(like);
    }

    // 특정 게시물에 대한 좋아요 목록 조회
    public List<Likes> getLikesByPost(Post post) {
        return likesRepository.findByPost(post);
    }

    // 특정 사용자가 좋아요를 누른 목록 조회
    public List<Likes> getLikesByMember(Member member) {
        return likesRepository.findByMember(member);
    }
}
