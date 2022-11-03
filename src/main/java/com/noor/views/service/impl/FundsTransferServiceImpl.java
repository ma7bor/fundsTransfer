package com.noor.views.service.impl;

import com.noor.views.dto.ExchangeRatesResponse;
import com.noor.views.dto.TransferRequest;
import com.noor.views.entity.Account;
import com.noor.views.exception.DepositException;
import com.noor.views.exception.ExchangeRatesException;
import com.noor.views.exception.InsufficientFundsException;
import com.noor.views.exception.NotFoundException;
import com.noor.views.repository.AccountRepository;
import com.noor.views.service.FundsTransferService;
import com.noor.views.service.ExchangeApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class FundsTransferServiceImpl implements FundsTransferService {


    private final AccountRepository accountRepository;
    private final ExchangeApi exchangeApi;

    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public FundsTransferServiceImpl(AccountRepository accountRepository, ExchangeApi exchangeApi) {
        this.accountRepository = accountRepository;
        this.exchangeApi = exchangeApi;
    }




    @Override
    public CompletableFuture<Account> getBalance(Long id) throws InterruptedException {
        Account account = accountRepository
                .getAccountByOwnerId(id).orElseThrow( () -> new NotFoundException(" " + id));
        Thread.sleep(1000);
        return CompletableFuture.completedFuture(account);
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<Account> deposit(Long ownerId, BigDecimal depositAmount, String currency) throws IOException, ExecutionException, InterruptedException {

        try{
            Account account = accountRepository.findById(ownerId)
                    .orElseThrow(() -> new NotFoundException(" " + ownerId));
            logger.info("Executing asynchronously - "+ Thread.currentThread().getName()+"  --deposit--  "+ ownerId + "  -- amount --  "+ depositAmount);
            BigDecimal finalBal;
            // case of same currency of the account
            if(account.getCurrency().equals(currency)){
                BigDecimal curBal = account.getBalance();
                finalBal = curBal.add(depositAmount);
            } else {
                // case of different currency
                CompletableFuture<ExchangeRatesResponse> convertedAmount = exchangeApi
                        .getExchangeData(depositAmount, account.getCurrency(), currency);
                if(!convertedAmount.get().getSuccess() && convertedAmount.get().getBalance() != null){
                    throw new ExchangeRatesException("APILAYER ERROR");
                }
                finalBal = account.getBalance().add(convertedAmount.get().getBalance());
            }
            account.setBalance(finalBal);
            accountRepository.save(account);

            return CompletableFuture.completedFuture(account);
        } catch (Exception e){
            throw new DepositException("because of : " + e.getMessage());
        }

    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<Account> withdraw(
            Long ownerId,
            BigDecimal withdrawAmount,
            String currency) throws IOException, ExecutionException, InterruptedException {
        System.out.println("Executing asynchronously - " + Thread.currentThread().getName() + "  --withdraw--  " + ownerId+ "  -- amount --  " + withdrawAmount);
        BigDecimal finalBal;
        Optional<Account> account = accountRepository.findById(ownerId);
        if (!account.isPresent())
            throw new NotFoundException("Resource Not found");

        // case of same currency of the account
        if(account.get().getCurrency().equals(currency)){

            // check if there's enough money to get
            if (account.get().getBalance().compareTo(withdrawAmount) < 0 ){
                throw new InsufficientFundsException(" for account with id # " + ownerId);
            }

            BigDecimal curBal = account.get().getBalance();
            finalBal = curBal.subtract(withdrawAmount);
        } else {
            // check if there's enough money to get
            if (account.get().getBalance().compareTo(withdrawAmount) < 0 ){
                throw new InsufficientFundsException(" for the account with id " + ownerId);
            }
            // case of different currency
            CompletableFuture<ExchangeRatesResponse> convertedAmount = exchangeApi
                    .getExchangeData(withdrawAmount, account.get().getCurrency(), currency);
            if(!convertedAmount.get().getSuccess() && convertedAmount.get().getBalance() != null){
                throw new ExchangeRatesException("APILAYER ERROR");
            }
            finalBal = account.get().getBalance().subtract(convertedAmount.get().getBalance());
        }
        account.get().setBalance(finalBal);
        accountRepository.save(account.get());
        return CompletableFuture.completedFuture(account.get());
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<Account> interFundTransaction(TransferRequest transferRequest) throws IOException, ExecutionException, InterruptedException {
        withdraw(transferRequest.getSenderId(),transferRequest.getBalance(), transferRequest.getCurrency());
        return deposit(transferRequest.getRecipientId(), transferRequest.getBalance(), transferRequest.getCurrency());

    }
}
