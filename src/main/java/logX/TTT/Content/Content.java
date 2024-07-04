package logX.TTT.Content;

import jakarta.persistence.*;
import logX.TTT.Post.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String type; // 'text' | 'image'

    @Column(nullable = false)
    private int index;

    @Column(nullable = false)
    private String data; // 글 내용 | 이미지URL
}