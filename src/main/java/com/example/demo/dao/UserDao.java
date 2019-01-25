package com.example.demo.dao;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * create by: one
 * create time:2019/1/23 18:19
 * 描述：用户相关DAO操作
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = "user";

    String INSERT_FIELDS = "name,password,salt,sex,email,phone,create_date";

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values(#{name},#{password},#{salt},#{sex},#{email},#{phone},#{createDate})"})
    @Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="user_id")
    int addUser(User user);

    @Select({"select salt from ",TABLE_NAME," where phone = #{str} OR email = #{str}"})
    String getSaltByStr(String str);

    @Select({"select * from ",TABLE_NAME," where password = #{password} AND  (name = #{str} OR  phone = #{str})"})
    User queryUser(String str,String password);

    @Select({"select * from ",TABLE_NAME," where  phone = #{phone} "})
    User isExist(Long phone);
}
