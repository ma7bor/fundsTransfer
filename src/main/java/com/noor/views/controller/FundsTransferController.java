package com.noor.views.controller;

import com.noor.views.dto.TransferRequest;
import com.noor.views.entity.Account;
import com.noor.views.service.FundsTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/fundsTransfers2")
public class FundsTransferController {

    private  ConcurrentMap<Long, Long> map;
    private FundsTransferService fundsTransferService;

    @Autowired
    public FundsTransferController(ConcurrentMap<Long, Long> map, FundsTransferService fundsTransferService) {
        this.map = map;
        this.fundsTransferService = fundsTransferService;
    }

    @GetMapping("/get/{id}")
    public Account getBal(@PathVariable("id") Long id) throws Exception {
        CompletableFuture<Account> account = fundsTransferService.getBalance(id);
        while (true) {
            if (account.isDone()) {
                break;
            }
        }
        return account.get();
    }

    @PostMapping("/deposit")
    public Account deposit(@RequestBody TransferRequest transferRequest) throws Exception {
        setLock(transferRequest.getRecipientId());
        CompletableFuture<Account> depositFuture = fundsTransferService
                .deposit(transferRequest.getRecipientId(),
                        transferRequest.getBalance(), transferRequest.getCurrency());

        while (true) {
            if (depositFuture.isDone()) {
                break;
            }
        }
        freeAccount(transferRequest.getRecipientId());
        return depositFuture.get();
    }

    @PostMapping("/withdraw")
    public Account withDraw(@RequestBody TransferRequest transferRequest) throws Exception {
        setLock(transferRequest.getSenderId());
        CompletableFuture<Account> withdrawFuture =
                fundsTransferService
                        .withdraw(transferRequest.getSenderId(),
                                transferRequest.getBalance(),transferRequest.getCurrency());
        while (true) {
            if (withdrawFuture.isDone()) {
                break;
            }
        }
        freeAccount(transferRequest.getSenderId());
        return withdrawFuture.get();
    }

    @PostMapping("/fundtransfer")
    public Account interFundTransferOps(@RequestBody TransferRequest transferRequest) throws Exception {
        setLock(transferRequest.getSenderId(),transferRequest.getRecipientId());
        CompletableFuture<Account> interFundTransferFuture =
                fundsTransferService
                        .interFundTransaction(transferRequest);
        while (true) {
            if (interFundTransferFuture.isDone()) {
                break;
            }
        }
        freeAccount(transferRequest.getSenderId());
        freeAccount(transferRequest.getRecipientId());
        return interFundTransferFuture.get();
    }

    private synchronized void setLock(Long fromAccount, Long toAccount) throws InterruptedException {
        while (this.map.containsKey(toAccount) || this.map.containsKey(fromAccount)) {
            wait();
        }
        this.map.putIfAbsent(fromAccount, (long) 1);
        this.map.putIfAbsent(toAccount, (long) 1);
    }

    private synchronized void setLock(Long accId) throws InterruptedException {
        while (this.map.containsKey(accId)) {
            wait();
        }
        this.map.putIfAbsent(accId, (long) 1);
    }

    private synchronized void freeAccount(Long accId) {
        this.map.remove(accId);
        notify();
    }
}
