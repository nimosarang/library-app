package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
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
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        user.returnBook(request.getBookName());

    }

}
