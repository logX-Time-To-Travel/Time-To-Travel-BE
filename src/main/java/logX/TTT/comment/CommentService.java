package logX.TTT.comment;


import logX.TTT.comment.model.CommentResponseDTO;
import logX.TTT.member.Member;
import logX.TTT.member.MemberRepository;
import logX.TTT.post.Post;
import logX.TTT.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    public CommentResponseDTO createComment(Long postId, Long memberId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content(content)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    public CommentResponseDTO getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));
        return convertToDTO(comment);
    }

    public List<CommentResponseDTO> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CommentResponseDTO updateComment(Long commentId, Long postId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID for the specified post"));

        comment.setContent(content);
        Comment updatedComment = commentRepository.save(comment);

        return convertToDTO(updatedComment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));

        commentRepository.delete(comment);
    }

    private CommentResponseDTO convertToDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getPost().getId(),
                comment.getMember().getUsername(),
                comment.getMember().getProfileImageUrl(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
