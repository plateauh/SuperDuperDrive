package com.udacity.jwdnd.course1.cloudstorage.persistence.mappers;

import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Credentials;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Najed Alseghair at 1/4/2024
 */
@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getCredentials(Integer userid);

    @Select("SELECT count(credentialid) FROM CREDENTIALS WHERE username = #{username} AND userid = #{userid}")
    int countCredentialByUsernameAndUserId(String username, Integer userid);

    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid) VALUES (#{credentialid}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    int insertCredential(Credentials credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteCredential(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialid}")
    void updateCredential(Credentials credential);
    
}
