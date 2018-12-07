package com.whb.provider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author labu
 * @Date 2018/11/29
 * @Description
 */
@Repository
public interface UserRep extends JpaRepository<User,Long> {
}
