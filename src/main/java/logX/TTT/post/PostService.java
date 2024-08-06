package logX.TTT.post;

import logX.TTT.location.Location;
import logX.TTT.location.model.LocationDTO;
import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.model.PostCreateDTO;
import logX.TTT.post.model.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<Location> locations = postCreateDTO.getLocations().stream()
                .map(dto -> Location.builder()
                        .name(dto.getName())
                        .latitude(dto.getLatitude())
                        .longitude(dto.getLongitude())
                        .build())
                .collect(Collectors.toList());

        Post post = Post.builder()
                .title(postCreateDTO.getTitle())
                .content(postCreateDTO.getContent())
                .member(member)
                .locations(locations)
                .views(new ArrayList<>())
                .build();

        locations.forEach(location -> location.setPost(post));

        Post savedPost = postRepository.save(post);
        return convertToResponseDTO(savedPost);
    }

    public PostResponseDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));

        post.addView();
        postRepository.save(post);

        return convertToResponseDTO(post);
    }

    public PostResponseDTO updatePost(Long id, PostCreateDTO postCreateDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));

        post.setTitle(postCreateDTO.getTitle());
        post.setContent(postCreateDTO.getContent());

        List<Location> locations = postCreateDTO.getLocations().stream()
                .map(dto -> Location.builder()
                        .name(dto.getName())
                        .latitude(dto.getLatitude())
                        .longitude(dto.getLongitude())
                        .build())
                .collect(Collectors.toList());

        post.setLocations(locations);
        locations.forEach(location -> location.setPost(post));

        Post updatedPost = postRepository.save(post);
        return convertToResponseDTO(updatedPost);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    private PostResponseDTO convertToResponseDTO(Post post) {
        List<LocationDTO> locationDTOs = post.getLocations().stream()
                .map(location -> new LocationDTO(location.getName(), location.getLatitude(), location.getLongitude()))
                .collect(Collectors.toList());

        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                locationDTOs,
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
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
}
