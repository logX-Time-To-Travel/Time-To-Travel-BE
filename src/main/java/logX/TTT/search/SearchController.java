package logX.TTT.search;

import logX.TTT.member.MemberService;
import logX.TTT.search.model.SearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;
    @Autowired
    private MemberService memberService;

    @PostMapping("/{username}")
    public ResponseEntity<Void> saveSearchQuery(@PathVariable String username, @RequestBody Map<String, String> requestBody) {
        String query = requestBody.get("keyword");
        Long memberId = memberService.getMemberIdByUsername(username);
        searchService.saveSearchQuery(memberId, query);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<SearchDTO>> getRecentSearchQueries(@PathVariable String username) {
        Long memberId = memberService.getMemberIdByUsername(username);
        List<SearchDTO> recentQueries = searchService.getRecentSearchQueries(memberId);
        return ResponseEntity.ok(recentQueries);
    }

}
