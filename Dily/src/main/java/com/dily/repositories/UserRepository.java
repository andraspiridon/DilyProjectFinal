package com.dily.repositories;

import com.dily.Mapper.UserMapper;
import com.dily.entities.User;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Andra on 4/11/2017.
 */

public interface UserRepository extends CrudRepository<User, Integer> ,JpaRepository<User,Integer>{

}
