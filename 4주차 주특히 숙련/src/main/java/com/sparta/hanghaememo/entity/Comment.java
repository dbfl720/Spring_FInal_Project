package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor// 생성자 없는 매개변수
@Builder
@AllArgsConstructor
public class Comment extends Timestamped {  //Timestamped가 상속이 되어서 같이 사용 될 것임.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;           //@Id만 사용할 경우 기본 키를 직접 할당해 주어야 합니다.
                               //기본 키를 직접 할당하는 대신 데이터베이스가 생성해주는 값을 사용하려면 @GeneratedValue를 사용해주면 됩니다.
                              //기본 설정 값으로 각 데이터베이스에 따라 기본키를 자동으로 생성한다.
                              //기본키의 제약조건
                               //null이면 안된다.
                               //유일하게 식별할 수 있어야한다.
                                //변하지 않는 값이어야 한다.


    @JsonIgnore  //??@JsonIgnore: Response에 해당 필드가 제외된다
    @ManyToOne(fetch = FetchType.LAZY)   //양방향 경우 cascade를 적을 때는, 연관관계 주인이 아니어도, 해당 데이터가 삭제된다.!!
    @JoinColumn(name = "Memo_Id")   //외래 키 매핑 시 사용 (name = 매핑할 외래키 이름)
    private Memo memo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;





    @Column(nullable = false)
    private String commentContents;

//    @Column(nullable = false)
//    private String commentUsername;

//생성자
    public Comment (CommentDto commentDto, User user, Memo memo) {  // 재료
        this.commentContents = commentDto.getCommentContents();  // 쿠키를 구운 것.
        this.memo = memo;
        this.user = user;
    }

    public void update(CommentDto commentDto) {

        this.commentContents = commentDto.getCommentContents();
    }
}