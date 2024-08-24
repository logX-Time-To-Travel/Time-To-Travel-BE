package logX.TTT.scrap;

import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.Post;
import logX.TTT.post.PostRepository;
import logX.TTT.scrap.model.ScrapDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void scrapPost(ScrapDTO scrapDTO) {
        Member member = memberRepository.findById(scrapDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Post post = postRepository.findById(scrapDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Scrap scrap = new Scrap();
        scrap.setMember(member);
        scrap.setPost(post);
        scrapRepository.save(scrap);
    }

    public List<ScrapDTO> getScraps(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        List<Scrap> scraps = scrapRepository.findByMember(member);

        return scraps.stream()
                .map(scrap -> new ScrapDTO(scrap.getId(), scrap.getMember().getId(), scrap.getPost().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteScrap(ScrapDTO scrapDTO) {
        Member member = memberRepository.findById(scrapDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Post post = postRepository.findById(scrapDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        scrapRepository.deleteByMemberAndPost(member, post);
    }
}