package com.noor.views;


import com.noor.views.entity.Account;
import com.noor.views.repository.AccountRepository;
import com.noor.views.service.impl.FundsTransferServiceImpl;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
@Ignore
public class AbstractTest {




    @Autowired @Mock
    protected FundsTransferServiceImpl fundsTransferServiceImpl;


    @Autowired @Mock
    protected AccountRepository accountRepository;

    protected Account account_1=null;
    protected Account account_2=null;


    protected void init() {
        System.out.println("init starts here");
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
}
