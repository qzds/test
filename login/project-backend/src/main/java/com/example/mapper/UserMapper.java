package com.example.mapper;

import com.example.entity.auth.Account;
import com.example.entity.user.AccountUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select studentId as username, password, email from users where studentId = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);

    @Insert("insert into users (studentId, password, email) values (#{username}, #{password}, #{email})")
    int createAccount(String username, String password, String email);

    // 根据 email 更新密码
    @Update("update users set password = #{password} where email = #{email}")
    int resetPasswordByEmail(String password, String email);

    @Results({
            @Result(property = "username",    column = "studentId"),
            @Result(property = "student_class",column = "student_class"),
            @Result(property = "major",       column = "major"),
            @Result(property = "grade",       column = "grade"),
            @Result(property = "phone",       column = "phone")
    })
    @Select("SELECT * FROM users WHERE studentId = #{text} OR email = #{text}")
    AccountUser findAccountUserByNameOrEmail(String text);


}
