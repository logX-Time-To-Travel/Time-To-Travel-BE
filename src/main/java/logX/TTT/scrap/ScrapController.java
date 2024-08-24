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
    @PostMapping
    public ResponseEntity<Void> scrapPost(@RequestBody ScrapDTO scrapDTO) {
        scrapService.scrapPost(scrapDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 특정 사용자의 스크랩 목록 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<ScrapDTO>> getScraps(@PathVariable Long memberId) {
        List<ScrapDTO> scraps = scrapService.getScraps(memberId);
        return ResponseEntity.ok(scraps);
    }
}