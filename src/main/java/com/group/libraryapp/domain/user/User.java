package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 20) //name varchar(20)
    private String name;

    private Integer age;
    // mappedBy -> 주인님에게 매여있다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) //연관관계의 주인이 가지고 있는 필드네임 "user"
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {
    }

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
        this.age = age;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void loanBook(String bookName) { //도메인 계층에 비니지스 로직이 들어갔다
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) { //유저가 본인과 연결되어 있는 대출기록들 중에서, 값이 들어오는 책 이름과 같은 걸 찾아서 반납처리하는 로직
        //userLoanHistories 중에서 해당 책 이름을 가진 기록을 찾아야함
        UserLoanHistory targetHistory = this.userLoanHistories.stream() //함수형 프로그래밍을 할 수 있게, stream 을 시작한다.
                .filter(history -> history.getBookName().equals(bookName))//들어오는 객체들(userLoanHistories 객체들) 중에서 다음 조건(filter(~~))을 충족하는 것만 필터링한다 (이 값이 파라미터로 받은 북네임과 같은게 걸리겠죠)
                .findFirst() //UserLoanHistory 중 책 이름이 bookName 과 같은 것만 남게 처리// 첫 번째로 해당하는 UserLoanHistory 를 찾는다 = 첫번째로 걸리는 걸 찾아서(findFirst 사용시 Optional 나옴)
                .orElseThrow(IllegalArgumentException::new); //혹시나 해당 책이름을 갖고있는 대출기록이 이 유저에 대해 없다면
        targetHistory.doReturn(); // doReturn 으로 반납 처리
    }


}
