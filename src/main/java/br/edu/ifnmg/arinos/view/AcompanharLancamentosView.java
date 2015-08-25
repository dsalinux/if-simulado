package br.edu.ifnmg.arinos.view;

import br.edu.ifnmg.arinos.entity.Aluno;
import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.chart.MeterGaugeChartModel;

@ManagedBean
@ViewScoped
public class AcompanharLancamentosView extends GenericCrudBean<Aluno> {

    private Integer quantidadeTotal;
    private Integer quantidadeLancadaDia1;
    private Integer quantidadeLancadaDia2;
    
    private MeterGaugeChartModel meterGaugeModel;

    @PostConstruct
    public void inicializar(){
        search(null);
    }
    
    public void search(){
        search(null);
    }
    
    @Override
    public void search(ActionEvent event) {
        try {
            doInTransaction(new PersistenceActionWithoutResult() {
                @Override
                public void execute(Session s) throws BusinessException {
                    Criteria criteriaDia1 = s.createCriteria(Aluno.class);
                    quantidadeTotal = ((Number) criteriaDia1.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                    criteriaDia1.add(Restrictions.isNotNull("quantidadeAcertos1"));
                    quantidadeLancadaDia1 = ((Number) criteriaDia1.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                    Criteria criteriaDia2 = s.createCriteria(Aluno.class);
                    criteriaDia2.add(Restrictions.isNotNull("quantidadeAcertos2"));
                    quantidadeLancadaDia2 = ((Number) criteriaDia2.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                }
            });
        } catch (BusinessException ex) {
            addMessage(getSeverityWarn(), ex.getMessage());
            Logger.getLogger(AcompanharLancamentosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MeterGaugeChartModel getMeterGaugeModel() {
        return meterGaugeModel;
    }

    public Integer getQuantidadeLancadaDia1() {
        return quantidadeLancadaDia1;
    }
    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }
    
    public Integer getQuantidadeLancadaEmPorcentagemDia1(){
        return (quantidadeLancadaDia1 * 100) / quantidadeTotal;
    }
    
    public Integer getQuantidadeLancadaEmPorcentagemDia2(){
        return (quantidadeLancadaDia2 * 100) / quantidadeTotal;
    }

    public Integer getQuantidadeLancadaDia2() {
        return quantidadeLancadaDia2;
    }
}
