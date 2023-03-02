package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 { //서비스계층의 주요 역할 : 비지니스로직 보유, 여러 SQL 접속해야하는 경우 트랜잭션 관리
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 아래 있는 함수가 시작될 때, start transaction;을 해준다 (트랜잭션을 시작!)
    // 힘수가 예외 없이 잘 끝났다면 commit
    // 혹시라도 문제가 있다면 rollback

    // 주의사항 : IOException 과 같은 Checked Exception 은 rollback 이 일어나지 않는다..!
    // <예시>
//    @Transactional
//    public void saveUser(UserCreateRequest request) throws IOException {
//        User u = userRepository.save(new User(request.getName(), request.getAge()));
//        throw new IOException();
//    }

    @Transactional
    public void saveUser(UserCreateRequest request) {
        User u = userRepository.save(new User(request.getName(), request.getAge()));
//        throw new IllegalArgumentException();  트랜잭션 작동 확인 차 고의 예외처리 던저보기
//        u.getId();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
//        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String name){
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }


}
