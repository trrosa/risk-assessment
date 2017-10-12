package br.com.trrosa.riskassessment.repository;

import br.com.trrosa.riskassessment.domain.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tiago
 */
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Long>{

}
