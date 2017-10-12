package br.com.trrosa.riskassessment.web.rest;

import br.com.trrosa.riskassessment.domain.RiskAssessment;
import br.com.trrosa.riskassessment.service.RiskAssessmentService;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tiago
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RiskAssessmentResource {

    private final RiskAssessmentService riskAssessmentService;

    @PostMapping("/risk-assessment")
    public ResponseEntity saveRiskAssessment(@NotNull @Valid @RequestBody RiskAssessment riskAssessment) {
        return Optional.of(riskAssessment)
                .filter(r -> r.getId() == null) //Updates are not allowed
                .map(r -> ResponseEntity.status(HttpStatus.CREATED).body(riskAssessmentService.save(r)))
                .orElse(ResponseEntity.badRequest().build());
    }

}
