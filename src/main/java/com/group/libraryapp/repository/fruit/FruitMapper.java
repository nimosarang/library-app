package com.group.libraryapp.repository.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FruitMapper {

    @Select("SELECT * FROM  fruits")
    List<Fruit> findAll();

    @Select("SELECT * FROM  fruits where name = #{name}")
    List<Fruit> findByName(@Param("name") String name);
}
