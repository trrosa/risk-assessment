package br.com.trrosa.riskassessment.service;

import br.com.trrosa.riskassessment.domain.RiskAssessment;
import br.com.trrosa.riskassessment.domain.enumeration.RiskType;
import br.com.trrosa.riskassessment.repository.RiskAssessmentRepository;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        riskAssessment.setRate(Optional.ofNullable(riskAssessment.getRisk()).map(RiskType::getRate).orElse(riskAssessment.getRate()));
        return riskAssessmentRepository.save(riskAssessment);
    }
    
    public Page<RiskAssessment> findAll(Pageable pageable){
        return riskAssessmentRepository.findAll(pageable);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Long id) {
        riskAssessmentRepository.delete(id);
    }

}
