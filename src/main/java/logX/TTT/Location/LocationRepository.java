package logX.TTT.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    // 글에 첨부된 장소 리스트 반환
    List<Location> findByPostId(Long postId);

    // 글에 첨부된 장소의 개수 반환
    long countByPostId(Long postId);
}
