package com.noor.views.initializer;

import com.noor.views.entity.Account;
import com.noor.views.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Init {

    @Value("${init.data}")
    private boolean init;

    private final AccountRepository accountRepository;

    private final Logger logger = LogManager.getLogger(this.getClass().getName());


    protected Account account_1=null;
    protected Account account_2=null;
    protected Account account_3=null;
    protected Account account_4=null;
    protected Account account_5=null;
    protected Account account_6=null;
    protected Account account_7=null;


    @PostConstruct
    public void init() {
        if (!init) {
            return;
        }
        saveAccounts();
    }


    private void saveAccounts() {
        try {
          account_1 = accountRepository.save(getAccount1());
          account_2 = accountRepository.save(getAccount2());
          account_3 = accountRepository.save(getAccount3());
          account_4 = accountRepository.save(getAccount4());
          account_5 = accountRepository.save(getAccount5());
          account_6 = accountRepository.save(getAccount6());
          account_7 = accountRepository.save(getAccount7());

        } catch (Exception e) {
            logger.error("Error creating accounts in init mode: ", e);
        }
    }

    protected Account getAccount1(){
        return Account.builder()
                .ownerId(100L)
                .balance(BigDecimal.valueOf(100L))
                .currency("EUR")
                .build();
    }
    protected Account getAccount2(){
        return Account.builder()
                .ownerId(200L)
                .balance(BigDecimal.valueOf(200))
                .currency("USD")
                .build();
    }

    protected Account getAccount3(){
        return Account.builder()
                .ownerId(300L)
                .balance(BigDecimal.valueOf(300))
                .currency("EUR")
                .build();
    }
    protected Account getAccount4(){
        return Account.builder()
                .ownerId(400L)
                .balance(BigDecimal.valueOf(400))
                .currency("USD")
                .build();
    }

    protected Account getAccount5(){
        return Account.builder()
                .ownerId(5000L)
                .balance(BigDecimal.valueOf(5000))
                .currency("MAD")
                .build();
    }

    protected Account getAccount6(){
        return Account.builder()
                .ownerId(1L)
                .balance(BigDecimal.ONE)
                .currency("EUR")
                .build();
    }

    protected Account getAccount7(){
        return Account.builder()
                .ownerId(1L)
                .balance(BigDecimal.TEN)
                .currency("EUR")
                .build();
    }


}
