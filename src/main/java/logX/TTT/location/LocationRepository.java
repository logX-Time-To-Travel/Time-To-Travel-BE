package logX.TTT.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l " +
            "WHERE l.latitude BETWEEN :southWestLat AND :northEastLat " +
            "AND l.longitude BETWEEN :southWestLng AND :northEastLng")
    List<Location> findByLocationRange(
            @Param("southWestLat") double southWestLat,
            @Param("southWestLng") double southWestLng,
            @Param("northEastLat") double northEastLat,
            @Param("northEastLng") double northEastLng
    );

    // 글에 첨부된 장소 리스트 반환
    List<Location> findByPostId(Long postId);

    // 글에 첨부된 장소의 개수 반환
    long countByPostId(Long postId);
}
