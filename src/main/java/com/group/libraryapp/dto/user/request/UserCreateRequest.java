package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {

    private String name;
    private Integer age; //null을 표현하기 위해 int 아닌 Integer 선택

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }



}
