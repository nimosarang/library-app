package com.group.libraryapp.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    // API 스펙 확인
    // HTTP Method : POST
    // HTTP Path : /book
    // HTTP Body (JSON)
    // 결과 반환 없음 (HTTP 상태 200 OK 이면 충분하다)

    //loanBook 서비스계층 메소드 만들기 ( 메소드 시그니쳐)

    //1. 책 정보를 가져온다.(책 이름기준으로)
    Optional<Book> findByName(String name);


}
