package com.noor.views.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@ToString
public class TransferRequest {


    private Long senderId;

    private Long recipientId;

    private BigDecimal balance;

    private String currency;

//    @NotNull
//    @Valid
//    private AmountAndCurrency balance;


}
