package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    //대출 정보 확인 위해 UserLoanHistoryRepository 필요
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository,
                       UserLoanHistoryRepository userLoanHistoryRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
        //추가해 스프링빈을 통해 주입받게 함
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        //1. 책 정보를 가져온다.(책 이름기준으로)
        // 책이 없는 경우는 예외 던짐, 책이 있다면 book 객체가 들어옴
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalAccessError::new);

        //2. 대출기록 정보를 확인해서 대출 중인지 확인한다
        //3. 만약에 확인했는데 대출 중이라면 예외를 발생키신다.
        // existsByBookNameAndIsReturn 을 호출했을 때,
        // 책이름_book.getName()과 false(대여중인)을 준다면,
        // 아래 if 문의 조건 전체가 true = 대여중인 책이 존재 / false = 대여중인 책이 없다 => "즉, 만약 반납되지 않은게 존재한다면"
        // 대여중인 책이 있는 경우 예외 던짐
        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            throw new IllegalArgumentException("현재 대출중인 책 입니다.");

        }

        //1~3번 책 정보를 가져와 대출여부 확인 후,  대출되지 않았다면 / 4번 ~ 유저 정보를 가져와 대출 기록을 만든다

        //4. 유저 정보를 가져온다.
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        user.loanBook(book.getName());

//        //5. 유저 정보와 책 정보를 기반으로 UserLoanHistory 를 저장 (Spring Data JPA 가 이 정보를 알아서 SQL 로 바꿔서 저장해줌)
//        userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));

    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        //1.유저 찾기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        user.returnBook(request.getBookName());

//        //                                                  유저 아이디와 '주어지는' 책 이름
//        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
//                .orElseThrow(IllegalArgumentException::new);// 유저 아이디와 책이름을 이용해서 대출기록 찾음
//        //대출 기록을 반납 처리
//        history.doReturn();

    }

}
