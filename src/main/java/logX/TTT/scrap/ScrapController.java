package logX.TTT.scrap;

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

    // 게시글 스크랩 요청
    @PostMapping("/{postId}/{memberId}")
    public ResponseEntity<Void> scrapPost(@PathVariable Long postId, @PathVariable Long memberId) {
        scrapService.scrapPost(postId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 특정 사용자의 스크랩 목록 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<ScrapDTO>> getScraps(@PathVariable Long memberId) {
        List<ScrapDTO> scraps = scrapService.getScraps(memberId);
        return ResponseEntity.ok(scraps);
    }

    @DeleteMapping("/{postId}/{memberId}")
    public ResponseEntity<Void> deleteScrap(@PathVariable Long postId, @PathVariable Long memberId) {
        scrapService.deleteScrap(postId, memberId);
        return ResponseEntity.noContent().build();
    }
}