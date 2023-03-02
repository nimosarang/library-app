package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //함수 이름만 작성하면 알아서 SQL 이 조립된다
    //find 라고 작성하면, 1개의 데이터만 가져온다.
    //By 뒤에 붙는 필드 이름으로 SELECT 쿼리의 WHERE 문이 작성된다.
    //SELECT * FROM user WHERE name = ?;
    Optional<User> findByName(String name); // 반환 타입은 User 이다. 유저가 없다면, null 반환 / 있다면 객체 반환

}
