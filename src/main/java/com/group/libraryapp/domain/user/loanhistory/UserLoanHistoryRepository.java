package com.group.libraryapp.domain.user.loanhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

    // select * from user_loan_history where book_name = ? and is_return = ?
    boolean existsByBookNameAndIsReturn(String name, boolean isReturn);

//    //대출기록을 찾는 함수 생성 (함수/메서드 시그니쳐, 사실상 SQL문 쏴주는 녀석)
//    Optional<UserLoanHistory> findByUserIdAndBookName(long userId, String bookName);
    // 반납기능 리팩토링을 통해(도메인계층에 비지니스로직 들어가도록, Jpa 연관관계 옵션 활용하여 최대한 도메인끼리 서로 협력하게끔) 위에꺼 이제 사용하지 않게 되어 지움


}
