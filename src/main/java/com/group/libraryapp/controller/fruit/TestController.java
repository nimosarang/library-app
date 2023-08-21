package com.group.libraryapp.controller.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.repository.fruit.FruitMapper;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final FruitMapper fruitMapper;

    public TestController(FruitMapper fruitMapper) {
        this.fruitMapper = fruitMapper;
    }

    @GetMapping("/test/mybatis")
    public void test(){
        List<Fruit> fruits = fruitMapper.findAll();
//        List<Fruit> fruits = fruitMapper.findByName("바나나");

        for (Fruit fruit : fruits) {
            System.out.println(fruit.getName());
            System.out.println(fruit.getPrice());
        }
    }
}
