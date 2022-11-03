package com.noor.views.repository;

import com.noor.views.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    CompletableFuture<Optional<Account>> findAccountByOwnerId(Long ownerId);
    Optional<Account> getAccountByOwnerId(Long id);
}
