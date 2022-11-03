package com.noor.views;

import com.noor.views.dto.ExchangeRatesResponse;
import com.noor.views.entity.Account;
import com.noor.views.exception.DepositException;
import com.noor.views.repository.AccountRepository;
import com.noor.views.service.ExchangeApi;
import com.noor.views.service.FundsTransferService;
import com.noor.views.service.impl.FundsTransferServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FundsTransferServiceTest{


    @Autowired @Mock
    protected FundsTransferService fundsTransferService;
    @Autowired @Mock
    protected ExchangeApi exchangeApi;
    @Autowired @Mock
    protected AccountRepository accountRepository;

    protected Account account_1=null;
    protected Account account_2=null;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    protected void init() {
        account_1 = accountRepository.save(getAccount_1());
        account_2 = accountRepository.save(getAccount_2());
    }

    protected void cleanDb(){
        accountRepository.deleteAll();
    }

    protected Account getAccount_1(){
        return Account.builder()
                .ownerId(1L)
                .balance(BigDecimal.ONE)
                .currency("EUR")
                .build();
    }
    protected Account getAccount_2(){
        return Account.builder()
                .ownerId(2L)
                .balance(BigDecimal.TEN)
                .currency("EUR")
                .build();
    }

    @Test
    public void should_Get_Balance_By_AccountId() throws Exception {
        CompletableFuture<Account> output = fundsTransferService.getBalance(2L);
        assertNotNull(output.get());
        assertThat(output.get().getOwnerId()).isEqualTo(2L);
        assertThat(output.get().getCurrency()).isEqualTo("EUR");
        assertThat(output.get().getBalance()).isEqualTo(BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void should_Get_Balance_By_AccountId2() throws ExecutionException, InterruptedException {
        CompletableFuture<Account>  outputt = fundsTransferService.getBalance(1L);
        assertNotNull(outputt);
        assertThat(outputt.get().getOwnerId()).isEqualTo(1L);
        assertThat(outputt.get().getCurrency()).isEqualTo("EUR");
        assertThat(outputt.get().getBalance()).isEqualTo(BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void should_Make_Deposit_Same_Currency() throws Exception {
        CompletableFuture<Account> outputt = fundsTransferService
                        .deposit(1L, BigDecimal.valueOf(30), "EUR");
        BigDecimal futureBal = BigDecimal.ONE.add(BigDecimal.valueOf(300));

        assertNotNull(outputt.get());
        assertThat(outputt.get().getOwnerId()).isEqualTo(1L);
        assertThat(outputt.get().getCurrency()).isEqualTo("EUR");
        assertThat(outputt.get().getBalance()).isEqualTo(futureBal.setScale(2, RoundingMode.HALF_UP));
    }
    @Test
    public void should_Make_Deposit_With_Currency_Conversion() throws Exception {
        CompletableFuture<Account> outputt = fundsTransferService
                        .deposit(1L, BigDecimal.valueOf(300), "MAD");
        CompletableFuture<ExchangeRatesResponse> futureBal = exchangeApi.getExchangeData(BigDecimal.valueOf(300),"EUR","MAD");
        assertNotNull(outputt.get());
        assertThat(outputt.get().getOwnerId()).isEqualTo(1L);
        assertThat(outputt.get().getCurrency()).isEqualTo("EUR");
        assertThat(outputt.get().getBalance()).isEqualTo(BigDecimal.ONE.add(futureBal.get().getBalance()));
    }

    @Test
    public void should_Make_withdraw_Same_Currency() throws IOException, ExecutionException, InterruptedException {
        CompletableFuture<Account> outputt = fundsTransferService
                .withdraw(2L, BigDecimal.valueOf(5), "EUR");
        BigDecimal futureBal = BigDecimal.TEN.subtract(BigDecimal.valueOf(5));
        assertNotNull(outputt.get());
        assertThat(outputt.get().getOwnerId()).isEqualTo(2L);
        assertThat(outputt.get().getCurrency()).isEqualTo("EUR");
        assertThat(outputt.get().getBalance()).isEqualTo(futureBal.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void should_Make_Withdraw_With_Currency_Conversion() throws IOException, ExecutionException, InterruptedException {
        CompletableFuture<Account> outputt = fundsTransferService
                .withdraw(2L, BigDecimal.valueOf(5), "MAD");
        CompletableFuture<ExchangeRatesResponse> futureBal = exchangeApi.getExchangeData(BigDecimal.valueOf(5),"EUR","MAD");

        assertNotNull(outputt.get());
        assertThat(outputt.get().getOwnerId()).isEqualTo(2L);
        assertThat(outputt.get().getCurrency()).isEqualTo("EUR");
        assertThat(outputt.get().getBalance())
                .isEqualTo(BigDecimal.TEN.subtract(futureBal.get().getBalance()));
    }



    @Test
    public void should_Get_Balance_By_AccountvvId2() throws Exception {
       CompletableFuture<Account> account = fundsTransferService
                .deposit(4988L, BigDecimal.TEN,"USD");
        assertNotNull(account.get());

    }

}
