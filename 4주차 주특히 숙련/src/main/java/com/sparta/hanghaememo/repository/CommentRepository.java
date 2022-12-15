package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional <Comment> findCommentByUserAndMemo (Long userId, Long memoId);    //db에서 코멘트를 찾아오는데, 서비스에서 user와 memo 넣어서 요청. // 코멘트 테이블에서 user와 memo가 일치하는 것을 찾는다.
                                                                            //optional은 값을 감싼다.    null로 인해서 발생하는 예외를 처리한다.
}