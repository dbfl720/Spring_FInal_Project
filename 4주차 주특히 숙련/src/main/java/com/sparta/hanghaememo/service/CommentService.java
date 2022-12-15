package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.dto.CommentResponseDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.exception.ErrorCode;
import com.sparta.hanghaememo.exception.RequestException;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.UserRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class CommentService {

    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public CommentResponseDto addComment(CommentDto commentDto, Long id, User user) {
//        String token = jwtUtil.resolveToken(request);//검증받은 토큰으로
//        Claims claims;
//
//        if (token != null) {
//            // Token 검증
//            if (jwtUtil.validateToken(token)) {
//                // 토큰에서 사용자 정보 가져오기
//                claims = jwtUtil.getUserInfoFromToken(token);  //토큰안에 있는 user 정보를 claims안에다가 넣어 놓은 상태
//            } else {
//                throw new RequestException(ErrorCode.BAD_TOKEN_400);
//            }
            userRepository.findByUsername(user.getUsername()).orElseThrow(     // 검증받은 유저에 정보를 가져 오겠다.
                    () -> new RequestException(ErrorCode.NULL_CONTENTS_400)
            );
            Optional<Memo> optionalBoard = memoRepository.findById(id);
            Memo memo = optionalBoard.orElseThrow(
                    () -> new RequestException(ErrorCode.NULL_CONTENTS_400)
            );

//            Comment comment = Comment.builder()
//                    .commentId(commentDto.getCommentId())
//                    .memo(memo)
//                    .commentUsername(claims.getSubject())
//                    .commentContents(commentDto.getCommentContents())
//                    .build();

            Comment comment = new Comment(commentDto, user, memo);  //객체 생성

            return new CommentResponseDto  (commentRepository.save(comment) );  //comment 테이블 안에 comment를 저장하겠다. bean등록을 안해서 새로 개체 생성을 해야하기 때문에 new를 씀.
//        }
//        System.out.println("64 ");
//        throw new RequestException(ErrorCode.NULL_TOKEN_400);  // 우리가 원하는 에러를 표시하겠다. 에러를 강제로 반환
    }


    @Transactional  //객체가 변화가 있을 때 변화를 감지한다.여러작업을 진행하다가 문제가 생겼을 경우 이전 상태로 롤백하기 위해 사용되는 것이 트랜잭션

    public CommentResponseDto updateComment(CommentDto commentDto, Long id, User user) {
//        String token = jwtUtil.resolveToken(request);
//        System.out.println("token = " + token);
//        Claims claims;

        userRepository.findByUsername(user.getUsername()).orElseThrow(     // 검증받은 유저에 정보를 가져 오겠다.
                () -> new RequestException(ErrorCode.NULL_CONTENTS_400)
        );

        Optional<Comment> optionalCommnet = commentRepository.findById(id);
        Comment comment = optionalCommnet.orElseThrow(
                () -> new RequestException(ErrorCode.NULL_COMMENT_400)
        );
            comment.update(commentDto);
            return new CommentResponseDto(comment);



//        if (token != null) {
//            // Token 검증
//            if (jwtUtil.validateToken(token)) {
//                // 토큰에서 사용자 정보 가져오기
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new RequestException(ErrorCode.BAD_TOKEN_400);
//            }
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new RequestException(ErrorCode.NULL_USER_400)
//            );
//
//            Optional<Comment> optionalCommnet = commentRepository.findById(id);
//            Comment comment = optionalCommnet.orElseThrow(
//                    () -> new RequestException(ErrorCode.NULL_COMMENT_400)
//            );
//            comment.update(commentDto);
//
//            if (comment.getUser().getUsername().equals(claims.getSubject())) {
//                comment.update(commentDto);
//               // return new CommentResponseDto(commentRepository.save(comment));
//            } else if (user.getRole() == UserRoleEnum.ADMIN) {
//                 comment.update(commentDto);
//               // return new CommentResponseDto(commentRepository.save(comment));
//            }else {
//                throw new RequestException(ErrorCode.NULL_USER_ACCESS_400);
//            }
//        }
//        System.out.println("104");
//        throw new RequestException(ErrorCode.NULL_TOKEN_400);
    }


    @Transactional
    public ResponseMsgDto deleteComment(Long commentId, User user) {
//        String token = jwtUtil.resolveToken(request);
//        System.out.println("token = " + token);
//        Claims claims;

//        Optional<Comment> optionalCommnet = commentRepository.findById(id);


        userRepository.findByUsername(user.getUsername()).orElseThrow(     // 검증받은 유저에 정보를 가져 오겠다.
                () -> new RequestException(ErrorCode.NULL_CONTENTS_400)
        );


        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RequestException(ErrorCode.NULL_COMMENT_400)
        );

        commentRepository.delete(comment);
        return new ResponseMsgDto("삭제 성공", HttpStatus.OK.value());
    }

//
//        if (token != null) {
//            // Token 검증
//            if (jwtUtil.validateToken(token)) {
//                // 토큰에서 사용자 정보 가져오기
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new RequestException(ErrorCode.BAD_TOKEN_400);
//            }
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new RequestException(ErrorCode.NULL_USER_400)
//            );
//
//            Optional<Comment> optionalCommnet = commentRepository.findById(id);
//            Comment comment = optionalCommnet.orElseThrow(
//                    () -> new RequestException(ErrorCode.NULL_COMMENT_400)
//            );
//
//            if (comment.getUser().getUsername().equals(claims.getSubject())) {
//                commentRepository.delete(comment);
////                return new CommentDto(comment);
//            }else if (user.getRole() == UserRoleEnum.ADMIN) {
//                commentRepository.delete(comment);
////                return new CommentDto(comment);
//            }else {
//                throw new RequestException(ErrorCode.NULL_USER_ACCESS_400);
//            }
//        }
//        System.out.println("140");
//        throw new RequestException(ErrorCode.NULL_TOKEN_400);
//    }
}
