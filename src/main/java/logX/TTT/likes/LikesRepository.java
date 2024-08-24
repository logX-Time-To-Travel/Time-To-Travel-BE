package logX.TTT.likes;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    // 특정 게시물에 대한 좋아요 목록 조회
    List<Likes> findByPost(Post post);

    // 특정 사용자가 좋아요를 누른 게시물 조회
    List<Likes> findByMember(Member member);

    Optional<Likes> findByPostAndMember(Post post, Member member);
    void deleteByPostAndMember(Post post, Member member);
}
