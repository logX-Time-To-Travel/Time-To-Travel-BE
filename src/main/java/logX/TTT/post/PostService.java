package logX.TTT.post;

import logX.TTT.content.Content;
import logX.TTT.content.model.ContentDTO;
import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.model.PostCreateDTO;
import logX.TTT.post.model.PostResponseDTO;
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

    public PostResponseDTO createPost(PostCreateDTO postCreateDTO) {
        Member member = memberRepository.findById(postCreateDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원 ID를 찾을 수 없습니다."));

        Post post = Post.builder()
                .title(postCreateDTO.getTitle())
                .member(member)
                .locations(postCreateDTO.getLocations())
                .build();

        List<Content> contents = postCreateDTO.getContent().stream()
                .map(contentDTO -> Content.builder()
                        .data(contentDTO.getData())
                        .post(post)
                        .build())
                .collect(Collectors.toList());

        post.setContentList(contents);
        Post savedPost = postRepository.save(post);
        return convertToResponseDTO(savedPost);
    }

    public PostResponseDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));
        return convertToResponseDTO(post);
    }

    public PostResponseDTO updatePost(Long id, PostCreateDTO postCreateDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));

        post.setTitle(postCreateDTO.getTitle());
        post.setLocations(postCreateDTO.getLocations());
        post.setContentList(postCreateDTO.getContent().stream()
                .map(contentDTO -> Content.builder()
                        .data(contentDTO.getData())
                        .post(post)
                        .build())
                .collect(Collectors.toList()));

        Post updatedPost = postRepository.save(post);
        return convertToResponseDTO(updatedPost);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    private PostResponseDTO convertToResponseDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContentList().stream()
                        .map(content -> new ContentDTO(content.getId(), content.getData()))
                        .collect(Collectors.toList()),
                post.getLocations(),
                post.getLikes(),
                post.getViews(),
                post.getCreatedAt()
        );
    }

    public List<PostResponseDTO> convertToResponseDTOs(List<Post> posts) {
        return posts.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        List<Post> posts = postRepository.findByMember(member);

        return posts.stream()
                .map(post -> new PostResponseDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getContentList().stream()
                                .map(content -> new ContentDTO(content.getId(), content.getData()))
                                .collect(Collectors.toList()),
                        post.getLocations(),
                        post.getLikes(),
                        post.getViews(),
                        post.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
