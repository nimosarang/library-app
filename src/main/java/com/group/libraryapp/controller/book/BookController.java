package com.group.libraryapp.controller.book;

import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import com.group.libraryapp.service.book.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //컨트롤러에서 api 를 받아 http 를 파싱하고
    //POST /book 을 연결시켜준 다음
    //HTTP Body 에 있는 JSON 을 BookCreateRequest(DTO)로 변경해주고
    //그 정보를 BookService 로 넘겨주게 된다
    //그러면 서비스 계층에서는 트랜잭션을 관리해주고
    //레포지토리를 호출해서 새로운 책을 저장한다
    @PostMapping("/book")
    public void saveBook(@RequestBody BookCreateRequest request) {
        bookService.saveBook(request);
    }

    //Post 니깐 HTTP Body 파싱하기 위해, RequestBody 걸어주고
    //BookLoanRequest 를 DTO 로 받아준다
    @PostMapping("/book/loan")
    public void loanBook(@RequestBody BookLoanRequest request){
        bookService.loanBook(request);
    }

    @PutMapping("/book/return")
    private void returnBook(@RequestBody BookReturnRequest request){
        bookService.returnBook(request);
    }

}

