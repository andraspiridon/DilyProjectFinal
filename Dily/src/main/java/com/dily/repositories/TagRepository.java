package com.dily.repositories;

import com.dily.entities.Tag;
import com.dily.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rusum on 13.04.2017.
 */
public interface TagRepository extends CrudRepository<Tag, Integer>,JpaRepository<Tag,Integer> {
}
