package logX.TTT.scrap;

import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.Post;
import logX.TTT.post.PostRepository;
import logX.TTT.scrap.model.ScrapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScrapService {
    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    //수정 할 예정
    public ScrapService(ScrapRepository scrapRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.scrapRepository = scrapRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public void scrapPost(ScrapDTO scrapDTO) {
        Member member = memberRepository.findById(scrapDTO.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));
        Post post = postRepository.findById(scrapDTO.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));

        Scrap scrap = new Scrap();
        scrap.setMember(member);
        scrap.setPost(post);
        scrapRepository.save(scrap);
    }

    public List<ScrapDTO> getScraps(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        List<Scrap> scraps = scrapRepository.findByMember(member);

        return scraps.stream()
                .map(scrap -> new ScrapDTO(scrap.getId(), scrap.getMember().getId(), scrap.getPost().getId()))
                .collect(Collectors.toList());
    }
}
