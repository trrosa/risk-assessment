package br.com.trrosa.riskassessment.service;

import br.com.trrosa.riskassessment.domain.RiskAssessment;
import br.com.trrosa.riskassessment.repository.RiskAssessmentRepository;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tiago
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RiskAssessmentService {
    
    private final RiskAssessmentRepository riskAssessmentRepository;
    
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RiskAssessment save(@NotNull @Valid RiskAssessment riskAssessment){
        return riskAssessmentRepository.save(riskAssessment);
    }

}
