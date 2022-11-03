package com.noor.views.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@With
@Builder
@Table(name = "account")
@Entity
public class Account {

    @Column(name = "owner_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ownerId;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "currency")
    private String currency;

//    @Column(name = "balance")
//    private BigDecimal balance;
//
//    @Column(name = "currency")
//    private Currency currency;

    //public Account removeMoney(MoneyWithCurrency balance, ExchangeService exchangeService) {
//    public Account removeMoney(MoneyWithCurrency balance) {
//
//        ExchangeDto exchangeDto = new ExchangeDto();
//        exchangeDto.setBalance(balance.getMoney());
//        exchangeDto.setCurrencyTo(balance.getCurrency());
//        exchangeDto.setCurrencyFrom(this.balance.getCurrency());
//
//        BigDecimal transferBalanceAfterCurrencyExchange = n
//                //exchangeService.exchange(exchangeDto);
//
//        if (this.getBalance().getMoney().compareTo(transferBalanceAfterCurrencyExchange.getMoney()) < 0 ){
//           throw new InsufficientFundsException();
//        }
//
//        this.balance = this.balance.substract(transferBalanceAfterCurrencyExchange.getMoney());
//        return this;
//    }

    //public Account addMoney(MoneyWithCurrency balance, ExchangeService exchangeService) {
//    public Account addMoney(MoneyWithCurrency balance) {
//
//        ExchangeDto exchangeDto = new ExchangeDto();
//        exchangeDto.setBalance(balance.getMoney());
//        exchangeDto.setCurrencyTo(balance.getCurrency());
//        exchangeDto.setCurrencyFrom(this.balance.getCurrency());
//
//        MoneyWithCurrency transferBalanceAfterCurrencyExchange = new MoneyWithCurrency(BigDecimal.ONE, Currency.CHF);
//
//        //exchangeService.exchange(exchangeDto);
//
//        this.balance = this.balance.add(transferBalanceAfterCurrencyExchange.getMoney());
//
//        return this;
//       }
}
