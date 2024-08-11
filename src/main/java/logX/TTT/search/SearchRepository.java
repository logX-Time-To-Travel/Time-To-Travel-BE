package logX.TTT.search;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findTop10ByMemberIdOrderBySearchedAtDesc(Long memberId);

    void deleteByMemberIdAndSearchHistoryId(Long memberId, Long searchHistoryId);
}