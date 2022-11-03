package com.noor.views.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

@With
@Getter
@ToString
public class AmountAndCurrency {
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final String currency;

    @JsonCreator
    public AmountAndCurrency(@JsonProperty("amount") BigDecimal amount,
                             @JsonProperty("currency") String currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public AmountAndCurrency subtract(BigDecimal amountToSubtract) {
        return this.withAmount(this.amount.subtract(amountToSubtract));
    }

    public AmountAndCurrency add(BigDecimal amountToAdd) {
        return this.withAmount(this.amount.add(amountToAdd));
    }
}

