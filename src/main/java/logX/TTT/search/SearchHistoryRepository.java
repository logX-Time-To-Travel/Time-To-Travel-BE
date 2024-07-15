package logX.TTT.search;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findTop10ByMemberIdOrderBySearchedAtDesc(Long memberId);
}