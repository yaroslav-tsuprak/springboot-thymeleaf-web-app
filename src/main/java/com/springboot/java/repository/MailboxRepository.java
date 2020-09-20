package com.springboot.java.repository;

import com.springboot.java.entity.Mailbox;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@EntityScan(basePackages = {"com.springboot.java.entity"})
public interface MailboxRepository extends JpaRepository<Mailbox, Integer> {
    Mailbox findByLogin(String login);
}
