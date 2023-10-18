package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id // 대표키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동으로 1씩 증가
    private Long id;
    @ManyToOne // Comment 엔티티와 Article 엔티티를 다대일 관계로 설정
    @JoinColumn(name = "article_id") // 외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article; // 해당 댓글의 부모 게시글

    @Column // 해당 필드를 테이블의 속성으로 매핑
    private String nickname; // 댓글 단 사람

    @Column // 해당 필드를 테이블의 속성으로 매칭
    private String body; // 댓글 본문

    public static Comment createComment(CommentDto dto, Article article) {
        //예외 발생
        if (dto.getId() != null) {
            throw new IllegalArgumentException("댓글 생성 실패. 댓글의 id가 없어야 합니다.");
        }
        if (dto.getArticleId() != article.getId()) {
            throw new IllegalArgumentException("댓글 생성 실패. 게시글의 id가 잘못됐습니다.");
        }

        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(), // 댓글 아이디
                article, // 부모 게시글
                dto.getNickname(), //댓글 닉네임
                dto.getBody() // 댓글 본문
        );
    }

    public void patch(CommentDto dto) {
        //예외 발생
        if (this.id != dto.getId()) {
            throw new IllegalArgumentException("댓글 수정 실패. 잘못된 id 입력됨");
        }

        //객체 갱신
        if (dto.getNickname() != null) {
            this.nickname = dto.getNickname();
        }
        if (dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }
}
