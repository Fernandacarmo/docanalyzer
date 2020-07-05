package com.visiblethread.docanalyzer.repository;

import com.visiblethread.docanalyzer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);

    List<User> findBy();

    List<User> findAllByDateBefore(Date toDate);

}
