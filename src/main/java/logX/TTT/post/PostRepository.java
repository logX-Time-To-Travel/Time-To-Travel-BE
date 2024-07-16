package logX.TTT.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 특정 회원이 작성한 모든 게시글 반환 메소드
    List<Post> findByMemberId(Long memberId);


    List<Post> findByUsername(String username);
    List<Post> findByTitleContainingOrContentContaining(String keyword);

}
