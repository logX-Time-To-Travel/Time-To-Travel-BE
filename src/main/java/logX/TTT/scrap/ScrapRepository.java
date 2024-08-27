package logX.TTT.scrap;

import logX.TTT.member.Member;
import logX.TTT.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findByMember(Member member);
    List<Scrap> findByPost(Post post);
    void deleteByMemberAndPost(Member member, Post post); // 특정 member와 post의 조합으로 삭제
    boolean existsByPostAndMember(Post post, Member member);
}
