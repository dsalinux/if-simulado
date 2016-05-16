package br.edu.ifnmg.arinos.view;

import br.edu.ifnmg.arinos.entity.vo.DistribuicaoProvasVO;
import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

@ManagedBean
@SessionScoped
public class DistribuicaoSalaView extends AbstractManager {

  private List<DistribuicaoProvasVO> distribuicaoSalaViews = new ArrayList<DistribuicaoProvasVO>();

  @PostConstruct
  public void init() {

  }

  public void listarDetalhesSala() {
    try {
      doInTransaction(new PersistenceActionWithoutResult() {
        @Override
        public void execute(Session s) throws BusinessException {
          SQLQuery q = s.createSQLQuery("SELECT sala, turma, count(turma) as quantidade FROM if_simulado.aluno group by turma,sala order by sala");
          q.addScalar("quantidade", new IntegerType());
          q.addScalar("sala", new IntegerType());
          q.addScalar("turma", new StringType());
          q.setResultTransformer(Transformers.aliasToBean(DistribuicaoProvasVO.class));
          distribuicaoSalaViews = q.list();
        }
      });
    } catch (BusinessException ex) {
      addMessage(getSeverityWarn(), ex.getMessage());
    }
  }

  public List<DistribuicaoProvasVO> lerDistribuicaoSalaViews(Integer sala) {
    List<DistribuicaoProvasVO> distribuicaoSala = new ArrayList<DistribuicaoProvasVO>();
    if (distribuicaoSalaViews != null) {
      for (DistribuicaoProvasVO distribuicaoSalaView : distribuicaoSalaViews) {
        distribuicaoSala.add(distribuicaoSalaView);
      }
    }
    return distribuicaoSala;
  }

  public List<Integer> getSalas() {
    List<Integer> salas = new ArrayList<Integer>();
    if (distribuicaoSalaViews != null) {
      for (DistribuicaoProvasVO sala : distribuicaoSalaViews) {
        if (!salas.contains(sala.getSala())) {
          salas.add(sala.getSala());
        }
      }
    }
    return salas;
  }

  public List<DistribuicaoProvasVO> getDistribuicaoSalaViews() {
    return distribuicaoSalaViews;
  }

}
