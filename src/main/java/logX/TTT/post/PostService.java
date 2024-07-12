package logX.TTT.post;

import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    public PostDTO createPost(String title, String content, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("post ID를 찾을 수 없습니다."));

        Post post = Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();

        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("post ID를 찾을 수 없습니다."));
        return convertToDTO(post);
    }

    public PostDTO updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("post ID를 찾을 수 없습니다."));

        post.setTitle(title);
        post.setContent(content);
        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("post ID를 찾을 수 없습니다."));

        postRepository.delete(post);
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getMember().getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }
}
