package com.noor.views.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExchangeRatesResponse {

    BigDecimal balance;
    Boolean success;
}
