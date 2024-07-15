package logX.TTT.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 특정 회원이 작성한 모든 게시글 반환 메소드
    List<Post> findByMemberId(Long memberId);

    // 주어진 위치 사이의 글 목록 반환하는 메소드
    @Query("SELECT DISTINCT p FROM Post p JOIN p.locations l " +
            "WHERE l.latitude BETWEEN :minLatitude AND :maxLatitude " +
            "AND l.longitude BETWEEN :minLongitude AND :maxLongitude")
    List<Post> findByLocationRange(
            @Param("minLatitude") double minLatitude,
            @Param("minLongitude") double minLongitude,
            @Param("maxLatitude") double maxLatitude,
            @Param("maxLongitude") double maxLongitude
    );

    List<Post> findByUsername(String username);
    List<Post> findByTitleContainingOrContentContaining(String keyword);

}
