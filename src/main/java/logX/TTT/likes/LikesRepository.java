package logX.TTT.likes;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByPost(Post post);
    List<Likes> findByMember(Member member);
    Optional<Likes> findByPostAndMember(Post post, Member member);
    void deleteByPostAndMember(Post post, Member member);
    boolean existsByPostAndMember(Post post, Member member);
    @Query("SELECT l.post FROM Likes l WHERE l.member.id = :memberId")
    List<Post> findPostsByMemberId(@Param("memberId") Long memberId);
}
