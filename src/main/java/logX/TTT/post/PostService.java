package logX.TTT.post;

import jakarta.servlet.http.HttpSession;
import logX.TTT.likes.LikesService;
import logX.TTT.location.Location;
import logX.TTT.location.model.LocationDTO;
import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.model.PostCreateDTO;
import logX.TTT.post.model.PostResponseDTO;
import logX.TTT.post.model.PostSummaryDTO;
import logX.TTT.scrap.ScrapService;
import logX.TTT.views.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LikesService likesService;
    private final ScrapService scrapService;
    private final HttpSession session;

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
                .thumbnail(postCreateDTO.getThumbnail())
                .member(member)
                .locations(locations)
                .likes(new ArrayList<>()) // 좋아요 초기화
                .views(new ArrayList<>()) // 조회수 초기화
                .comments(new ArrayList<>()) // 댓글 초기화
                .build();

        locations.forEach(location -> location.setPost(post));

        Post savedPost = postRepository.save(post);
        return convertToResponseDTO(savedPost);
    }

    public PostResponseDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));

        incrementViewCount(post); // 조회수 증가 메소드 호출

        return convertToResponseDTO(post);
    }

    public PostResponseDTO updatePost(Long id, PostCreateDTO postCreateDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));

        post.setTitle(postCreateDTO.getTitle());
        post.setContent(postCreateDTO.getContent());
        post.setThumbnail(postCreateDTO.getThumbnail());

        post.getLocations().clear(); // 기존 locations 리스트 삭제
        postRepository.flush(); // 기존 데이터가 반영

        // 새로운 locations 리스트 설정
        List<Location> locations = postCreateDTO.getLocations().stream()
                .map(dto -> Location.builder()
                        .name(dto.getName())
                        .latitude(dto.getLatitude())
                        .longitude(dto.getLongitude())
                        .post(post) // Post <-> Location 연관관계 설정
                        .build())
                .collect(Collectors.toList());

        post.getLocations().addAll(locations);

        Post updatedPost = postRepository.save(post);
        return convertToResponseDTO(updatedPost);
    }


    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트 ID를 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    private int getPostCountByMemberId(Long memberId) {
        return postRepository.countByMemberId(memberId);
    }

    private void incrementViewCount(Post post) {
        // 조회수 증가 로직 구현
        Views view = new Views(); // Views 객체 생성
        view.setPost(post); // post 필드 설정
        post.getViews().add(view); // 조회수 리스트에 추가
        postRepository.save(post); // 변경된 상태를 저장
    }

    private PostResponseDTO convertToResponseDTO(Post post) {
        List<LocationDTO> locationDTOs = post.getLocations().stream()
                .map(location -> new LocationDTO(location.getName(), location.getLatitude(), location.getLongitude()))
                .collect(Collectors.toList());

        boolean isLiked = false;
        boolean isScrapped = false;

        // 세션에서 memberId 가져오기
        Long memberId = (Long) session.getAttribute("member");
        System.out.println("memberId = " + memberId);
        if (memberId != null) {
            isLiked = likesService.isPostLikedByUser(post.getId(), memberId);
            isScrapped = scrapService.isScrappedByUser(post.getId(), memberId);
        }


        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getMember().getUsername(),
                post.getMember().getProfileImageUrl(),
                post.getMember().getIntroduction(),
                post.getContent(),
                locationDTOs,
                getPostCountByMemberId(post.getMember().getId()),
                post.getLikes().size(),
                post.getViews().size(),
                post.getComments().size(),
                post.getCreatedAt(),
                isLiked,
                isScrapped,
        );
    }

    private PostSummaryDTO convertToSummaryDTO(Post post) {
        return new PostSummaryDTO(
                post.getId(),
                post.getTitle(),
                post.getLikes().size(),
                post.getViews().size(),
                post.getThumbnail(),
                post.getCreatedAt()
        );
    }

    public List<PostResponseDTO> convertToResponseDTOs(List<Post> posts) {
        return posts.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PostSummaryDTO> convertToSummaryDTOs(List<Post> posts) {
        return posts.stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }

    public List<PostSummaryDTO> getPostsByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        List<Post> posts = postRepository.findByMember(member);

        return convertToSummaryDTOs(posts);
    }
}

