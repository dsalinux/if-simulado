package br.edu.ifnmg.arinos.view;

import br.edu.ifnmg.arinos.entity.Aluno;
import br.edu.ifnmg.arinos.util.Context;
import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@ManagedBean
@SessionScoped
public class LancarAcertosView extends GenericCrudBean<Aluno> {
  
  private static final long serialVersionUID = -7554148384570296256L;

  private Integer sala;
  private boolean buscouAutomatico = false;

  public void save(Aluno a) {
    final Aluno aluno = a;
    try {
      doInTransaction(new PersistenceActionWithoutResult() {
        @Override
        public void execute(Session s) throws BusinessException {
          aluno.setSession(s);
          aluno.save();
          addMessage(getSeverityInfo(), "Nota do aluno " + aluno.getNome() + " alterada com sucesso!");
        }
      });
    } catch (BusinessException ex) {
      addMessage(getSeverityWarn(), ex.getMessage());
      Logger.getLogger(LancarAcertosView.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void buscarAutomaticamente(){
      search(null);
      buscouAutomatico = true;
      addMessage("Já buscou");
      
  }

  @Override
  public void search(ActionEvent event) {
    try {
      if (Context.getLogin() != null) {
        sala = Context.getLogin().getSalaDefault();
      }
      if (sala == null) {
        addMessage(getSeverityWarn(), "Sala do usuário não foi configurada!");
      }
      doInTransaction(new PersistenceActionWithoutResult() {
        @Override
        public void execute(Session s) throws BusinessException {
          Criteria criteria = s.createCriteria(Aluno.class);
          criteria.add(Restrictions.eq("sala", sala));
          criteria.addOrder(Order.asc("nome"));
          setEntitys(criteria.list());
          if (getEntitys() == null || getEntitys().isEmpty()) {
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

  public boolean isBuscouAutomatico() {
    return buscouAutomatico;
  }
  
}
