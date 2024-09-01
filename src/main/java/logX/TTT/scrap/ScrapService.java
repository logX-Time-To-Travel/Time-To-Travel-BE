package logX.TTT.scrap;

import logX.TTT.likes.Likes;
import logX.TTT.likes.LikesRepository;
import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.Post;
import logX.TTT.post.PostRepository;
import logX.TTT.post.model.PostSummaryDTO;
import logX.TTT.scrap.model.ScrapDTO;
import logX.TTT.views.ViewsRepository;
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
    private final LikesRepository likesRepository;
    private final ViewsRepository viewsRepository;

    public void scrapPost(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Scrap scrap = new Scrap();
        scrap.setMember(member);
        scrap.setPost(post);
        scrapRepository.save(scrap);
    }

    public List<PostSummaryDTO> getScrappedPostsByMember(Member member) {
        List<Scrap> scrappedList = scrapRepository.findByMember(member); // 사용자가 좋아요한 Likes 목록 조회

        return scrappedList.stream().map(like -> {
            Post scrappedPost = like.getPost();
            int likeCount = likesRepository.findByPost(scrappedPost).size(); // 해당 게시물의 좋아요 수
            int viewCount = viewsRepository.findByPost(scrappedPost).size(); // 해당 게시물의 조회수

            // PostSummaryDTO 생성
            return new PostSummaryDTO(
                    scrappedPost.getId(),
                    scrappedPost.getTitle(),
                    likeCount,
                    viewCount,
                    scrappedPost.getThumbnail(),
                    scrappedPost.getCreatedAt()
            );
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteScrap(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        scrapRepository.deleteByMemberAndPost(member, post);
    }

    public boolean isScrappedByUser(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        return scrapRepository.existsByPostAndMember(post, member);
    }
}