package br.edu.ifnmg.arinos.view;

import br.edu.ifnmg.arinos.entity.Aluno;
import br.edu.ifnmg.arinos.util.Context;
import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@ManagedBean
@ViewScoped
public class LancarAcertosView extends GenericCrudBean<Aluno> {

    private Integer sala;
    
    @PostConstruct
    public void inicializar(){
        if(Context.getLogin() != null)
            sala = Context.getLogin().getSalaDefault();
        if(sala == null){
            addMessage(getSeverityWarn(), "Sala do usuário não foi configurada!");
        }
        search(null);
    }
    
    public void save(Aluno a){
        final Aluno aluno = a;
        try {
            doInTransaction(new PersistenceActionWithoutResult() {
                @Override
                public void execute(Session s) throws BusinessException {
                    aluno.setSession(s);
                    aluno.save();
                    addMessage(getSeverityInfo(), "Nota do aluno " + aluno.getNome()+" alterada com sucesso!");
                }
            });
        } catch (BusinessException ex) {
            addMessage(getSeverityWarn(), ex.getMessage());
            Logger.getLogger(LancarAcertosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void search(ActionEvent event) {
        try {
            doInTransaction(new PersistenceActionWithoutResult() {
                @Override
                public void execute(Session s) throws BusinessException {
                    Criteria criteria = s.createCriteria(Aluno.class);
                    criteria.add(Restrictions.eq("sala", sala));
                    criteria.addOrder(Order.asc("nome"));
                    setEntitys(criteria.list());
                    if(getEntitys() == null || getEntitys().isEmpty()){
                        addMessage(getSeverityWarn(), "Nenhum aluno encontrado para a sala: " + sala);
                    }
                }
            });
        } catch (BusinessException ex) {
            addMessage(getSeverityWarn(), ex.getMessage());
            Logger.getLogger(LancarAcertosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Integer getSala() {
        return sala;
    }
    
}
