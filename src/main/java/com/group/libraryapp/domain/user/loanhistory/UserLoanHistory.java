package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

    private String bookName;

    private boolean isReturn; // 0=대출, 1=반납

    protected UserLoanHistory(){}

    public UserLoanHistory(User user, String bookName) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false; //처음은 무조건 대출상태가 아님
    }

    public void doReturn(){ //대출 기록에 대한 리턴 작업
        this.isReturn = true;

    }

    public String getBookName() {
        return this.bookName;
    }

}
