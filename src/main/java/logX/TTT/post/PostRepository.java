package logX.TTT.post;

import logX.TTT.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 특정 회원이 작성한 모든 게시글 반환 메소드
    List<Post> findByMemberId(Long memberId);

    @Query("SELECT p FROM Post p WHERE p.member.username = :username")
    List<Post> findByUsername(String username);


    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN p.contentList c " +
            "WHERE p.title LIKE %:keyword% OR c.data LIKE %:query%")
    List<Post> findByTitleContainingOrContentDataContaining(@Param("query") String keyword);

    List<Post> findByMember(Member member);
}
