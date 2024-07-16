package logX.TTT.post;

import logX.TTT.content.Content;
import logX.TTT.content.model.ContentDTO;
import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    public PostDTO createPost(String title, List<ContentDTO> contentList, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 ID를 찾을 수 없습니다."));

        Post post = Post.builder()
                .title(title)
                .member(member)
                .build();

        List<Content> contents = contentList.stream()
                .map(contentDTO -> Content.builder()
                        .type(contentDTO.getType())
                        .data(contentDTO.getData())
                        .post(post)
                        .build())
                .collect(Collectors.toList());

        post.setContentList(contents);

        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));
        return convertToDTO(post);
    }

    public PostDTO updatePost(Long id, String title, List<ContentDTO> contentList) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));

        post.setTitle(title);
        post.setContentList(contentList.stream()
                .map(contentDTO -> Content.builder()
                        .type(contentDTO.getType())
                        .data(contentDTO.getData())
                        .post(post)
                        .build())
                .collect(Collectors.toList()));

        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));

        postRepository.delete(post);
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getMember().getId(),
                post.getTitle(),
                post.getContentList().stream()
                        .map(content -> new ContentDTO(content.getId(), content.getType(), content.getData()))
                        .collect(Collectors.toList()),
                post.getCreatedAt()
        );
    }

    public List<PostDTO> getPostsByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        List<Post> posts = postRepository.findByMember(member);

        return posts.stream()
                .map(post -> new PostDTO(post.getId(), member.getId(), post.getTitle(), post.getContentList().stream()
                        .map(content -> new ContentDTO(content.getId(), content.getType(), content.getData()))
                        .collect(Collectors.toList()), post.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
