package logX.TTT.views;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewsRepository extends JpaRepository<Views, Long> {
    // 특정 게시물에 대한 조회수 목록 조회
    List<Views> findByPost(Post post);

    // 특정 사용자의 조회수 목록 조회
    List<Views> findByMember(Member member);
}
