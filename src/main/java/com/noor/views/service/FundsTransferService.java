package com.noor.views.service;

import com.noor.views.dto.TransferRequest;
import com.noor.views.entity.Account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface FundsTransferService {
    CompletableFuture<Account> getBalance(Long id) throws InterruptedException;

    CompletableFuture<Account> deposit(Long accountId, BigDecimal depositAmount, String currency) throws Exception;

    CompletableFuture<Account> withdraw(Long accountId, BigDecimal withdrawAmount,  String currency) throws InterruptedException, IOException, ExecutionException;

    CompletableFuture<Account> interFundTransaction(TransferRequest transferRequest) throws InterruptedException, IOException, ExecutionException;

}
