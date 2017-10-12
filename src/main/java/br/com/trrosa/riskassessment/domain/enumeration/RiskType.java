package br.com.trrosa.riskassessment.domain.enumeration;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author tiago
 */
@Getter
@AllArgsConstructor
public enum RiskType {
    A(BigDecimal.valueOf(0)), B(BigDecimal.valueOf(10)), C(BigDecimal.valueOf(20));
    private final BigDecimal rate;  
    
}
