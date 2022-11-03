package com.noor.views.dto;

import lombok.*;

import javax.persistence.Column;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@With
@Builder
public class AccountDto {

    private Long ownerId;
    private BigDecimal balance;
    private String currency;
}
