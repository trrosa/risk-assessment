package br.com.trrosa.riskassessment.domain;

import br.com.trrosa.riskassessment.domain.enumeration.RiskType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author tiago
 */
@Entity
@Table(name = "RISK_ASSESSMENT")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskAssessment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255, min = 1)
    @Column(name = "CUSTOMER_NAME", nullable = false, length = 255)
    private String customerName;

    @NotNull
    @Min(0)
    @Column(name = "CREDIT_LIMIT", nullable = false)
    private BigDecimal creditLimit;
    
    @NotNull
    @Column(name = "RISK", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private RiskType risk;
    
    @Column(name="RATE", nullable = false)
    private BigDecimal rate;
    
    @PrePersist
    @PreUpdate
    private void prePersistOrUpdate(){
        setRate(Optional.ofNullable(risk).map(RiskType::getRate).orElse(rate));
    }

}
