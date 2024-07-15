package logX.TTT.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public void saveSearchQuery(Long memberId, String query) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setMemberId(memberId);
        searchHistory.setQuery(query);
        searchHistoryRepository.save(searchHistory);

        List<SearchHistory> searchHistoryList = searchHistoryRepository.findTop10ByMemberIdOrderBySearchedAtDesc(memberId);
        if(searchHistoryList.size() > 10) {
            searchHistoryRepository.delete(searchHistoryList.get(searchHistoryList.size() - 1));
        }
    }

    public List<String> getRecentSearchQueries(Long memberId) {
        List<SearchHistory> searchHistories = searchHistoryRepository.findTop10ByMemberIdOrderBySearchedAtDesc(memberId);
        return searchHistories.stream()
                .map(SearchHistory::getQuery)
                .collect(Collectors.toList());
    }
}
