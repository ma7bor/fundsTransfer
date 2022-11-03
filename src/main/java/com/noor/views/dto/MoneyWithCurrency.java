package com.noor.views.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@With
@NoArgsConstructor
@EqualsAndHashCode
public class MoneyWithCurrency implements Serializable {

    @NotNull
    private BigDecimal money;
   @NotNull
    private String currency;

    @JsonCreator
    public MoneyWithCurrency(@JsonProperty("amount") BigDecimal money,
                             @JsonProperty("currency") String currency) {
        this.money = money.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public MoneyWithCurrency substract(BigDecimal amountToWithdraw) {
        return this.withMoney(this.money.subtract(amountToWithdraw));

    }

    public MoneyWithCurrency add(BigDecimal amountToAdd) {
        return this.withMoney(this.money.add(amountToAdd));
    }

}
