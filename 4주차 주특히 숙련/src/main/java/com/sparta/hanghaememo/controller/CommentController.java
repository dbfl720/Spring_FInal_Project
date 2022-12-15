

package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.dto.CommentResponseDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController    //@RestController을 표시하면 모든 메소드가 뷰 대신 객체로 작성됩니다.
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class CommentController {
    private final CommentService commentService;  //리소스 낭비 막고자, final 지정.

    // 댓글 추가
    @PostMapping("/api/comment/{id}")   //addcomment 메서드
    public CommentResponseDto addComment(@RequestBody CommentDto commentDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.addComment(commentDto, id, userDetails.getUser());
    }


    // 클라이언트에서 서버로 필요한 데이터를 요청하기 위해 JSON 데이터를 요청 본문에 담아서 서버로 보내면,
    // 서버에서는 @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에 담긴 값들을 자바객체로 변환시켜, 객체에 저장한다.


    // 댓글 수정
    @PutMapping("/api/comment/{id}")
    public CommentResponseDto updateComment(@RequestBody CommentDto commentDto, @PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(commentDto, id, userDetails.getUser() );
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment/{id}")
    public ResponseMsgDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.deleteComment(id, userDetails.getUser());
    }
//    @DeleteMapping("/api/memo/{memoId}/comment")
//    public ResponseMsgDto deleteComment(@PathVariable Long memoId,@AuthenticationPrincipal UserDetailsImpl userDetails){
//        commentService.deleteComment(memoId, id);
//        return new ResponseMsgDto("댓글 성공", HttpStatus.OK.value());
//    }


}