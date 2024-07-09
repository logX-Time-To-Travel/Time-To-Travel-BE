package logX.TTT.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    void deleteByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
