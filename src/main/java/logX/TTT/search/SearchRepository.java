package logX.TTT.search;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findTop10ByMemberIdOrderBySearchedAtDesc(Long memberId);

    void deleteByMemberIdAndId(Long memberId, Long id);
}