package logX.TTT.search;

import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchHistoryRepository;
    @Autowired
    private MemberRepository memberRepository;

    public void saveSearchQuery(Long memberId, String query) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        Search searchHistory = new Search();
        searchHistory.setMember(member);
        searchHistory.setQuery(query);
        searchHistoryRepository.save(searchHistory);

        List<Search> searchHistoryList = searchHistoryRepository.findTop10ByMemberIdOrderBySearchedAtDesc(memberId);
        if (searchHistoryList.size() > 10) {
            searchHistoryRepository.delete(searchHistoryList.get(searchHistoryList.size() - 1));
        }
    }

    public List<String> getRecentSearchQueries(Long memberId) {
        List<Search> searchHistories = searchHistoryRepository.findTop10ByMemberIdOrderBySearchedAtDesc(memberId);
        return searchHistories.stream()
                .map(Search::getQuery)
                .collect(Collectors.toList());
    }
}
