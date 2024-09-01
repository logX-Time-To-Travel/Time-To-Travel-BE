package logX.TTT.scrap;

import logX.TTT.member.Member;
import logX.TTT.member.MemberService;
import logX.TTT.post.model.PostSummaryDTO;
import logX.TTT.scrap.model.ScrapDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scraps")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;
    private final MemberService memberService;

    // 게시글 스크랩 요청
    @PostMapping("/{postId}/{memberId}")
    public ResponseEntity<Void> scrapPost(@PathVariable Long postId, @PathVariable Long memberId) {
        scrapService.scrapPost(postId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 특정 사용자의 스크랩 목록 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<PostSummaryDTO>> getLikePosts(@PathVariable Long memberId) {
        try {
            Member member = memberService.getMemberById(memberId);
            List<PostSummaryDTO> posts = scrapService.getScrappedPostsByMember(member);
            return ResponseEntity.ok(posts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{postId}/{memberId}")
    public ResponseEntity<Void> deleteScrap(@PathVariable Long postId, @PathVariable Long memberId) {
        scrapService.deleteScrap(postId, memberId);
        return ResponseEntity.noContent().build();
    }
}