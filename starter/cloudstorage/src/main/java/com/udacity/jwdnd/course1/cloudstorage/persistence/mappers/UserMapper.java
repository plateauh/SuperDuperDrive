package com.udacity.jwdnd.course1.cloudstorage.persistence.mappers;

import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Mapper
public interface UserMapper {
    // if storing wasn't successful, remember to change the parameters to User object
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    int insertUser(Users user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    Users getUser(String username);
}
