package com.huige.learning.user;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("<script>" +
            "SELECT * FROM app_user WHERE 1=1" +
            "<if test='name != null and name != \"\"'> AND name LIKE CONCAT('%', #{name}, '%')</if>" +
            "<if test='email != null and email != \"\"'> AND email LIKE CONCAT('%', #{email}, '%')</if>" +
            "</script>")
    List<User> findByNameOrEmail(@Param("name") String name, @Param("email") String email);

    @Select("SELECT * FROM app_user WHERE id = #{id}")
    User findById(Long id);

    @Insert("INSERT INTO app_user (name, age, email, phone) VALUES (#{name}, #{age}, #{email}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE app_user SET name=#{name}, age=#{age}, email=#{email}, phone=#{phone} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM app_user WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT * FROM app_user WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("<script>" +
            "INSERT INTO app_user (name, age, email, phone) VALUES " +
            "<foreach collection='list' item='u' separator=','>" +
            "(#{u.name}, #{u.age}, #{u.email}, #{u.phone})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("list") List<User> users);
}
