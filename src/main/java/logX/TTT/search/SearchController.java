package logX.TTT.search;

import logX.TTT.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
