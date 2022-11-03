package com.noor.views.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExchangeDto {

    private BigDecimal balance;
    private String currencyFrom;
    private String currencyTo;
}
