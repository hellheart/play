package com.huige.learning.openapi;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OpenApiMapper {

    @Select("<script>" +
            "SELECT * FROM open_api WHERE 1=1" +
            "<if test='name != null and name != \"\"'> AND name LIKE CONCAT('%', #{name}, '%')</if>" +
            "<if test='category != null and category != \"\"'> AND category = #{category}</if>" +
            "ORDER BY id" +
            "</script>")
    List<OpenApi> search(@Param("name") String name, @Param("category") String category);

    @Select("SELECT DISTINCT category FROM open_api WHERE category IS NOT NULL AND category != '' ORDER BY category")
    List<String> categories();

    @Select("SELECT * FROM open_api WHERE id = #{id}")
    OpenApi findById(Long id);

    @Insert("INSERT INTO open_api (name, description, url, category, method, need_auth, status) " +
            "VALUES (#{name}, #{description}, #{url}, #{category}, #{method}, #{needAuth}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OpenApi api);

    @Delete("DELETE FROM open_api")
    int deleteAll();
}
