package com.lr.jpa.serviceimpl;

import com.lr.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserServiceImpl extends JpaRepository<User, Integer> {

/*    @Query("from User")
    List<User> getAllUser();

    //@Query 注解，并提供一个查询语句作为参数，Spring Data JPA 在创建代理对象时，便以提供的查询语句来实现其功能。
    @Query("from User where username = :username")
    User findUser(@Param("username") String name);*/

    User findByUsername(String username);
}
