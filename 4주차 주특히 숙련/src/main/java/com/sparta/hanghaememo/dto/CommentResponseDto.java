package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor  //파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private Long mId;

    private String commentContents;


    //생성자
    public CommentResponseDto(Comment comment){

        this.commentId = comment.getCommentId();
        this.mId = comment.getMemo().getId();
        this.commentContents = comment.getCommentContents();
    }
}



