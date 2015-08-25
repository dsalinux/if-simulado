package br.edu.ifnmg.arinos.view;

import br.edu.ifnmg.arinos.entity.AcertosTurmas;
import br.edu.ifnmg.arinos.entity.Aluno;
import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

@ManagedBean
@SessionScoped
public class GerenciarAlunoView extends GenericCrudBean<Aluno> {

    private List<AcertosTurmas> turmas;
    private Integer quantidadeSalas = 10;
    
    @PostConstruct
    public void init(){
        
    }
    
    public void salvarNotas(){
        try {
            doInTransaction(new PersistenceActionWithoutResult() {
                @Override
                public void execute(Session s) throws BusinessException {
                    List<Aluno> alunos = getEntitys();
                    for (Aluno aluno : alunos) {
                        aluno.setSession(s);
                        aluno.save();
                    }
                }
            });
            
        } catch (BusinessException e){
            addMessage(e.getMessage());
        }
    
}

    public void salvarOrdenado() {
        try {
            Arrays.sort(getEntitys().toArray());
            doInTransaction(new PersistenceActionWithoutResult() {
                @Override
                public void execute(Session s) throws BusinessException {
                    int quantidadeAlunosNaoDivididos = getEntitys().size()%quantidadeSalas;
                    int quantidadeAlunosSala = (getEntitys().size()-quantidadeAlunosNaoDivididos)/quantidadeSalas;
                    
                    int[] distribuicao = new int[quantidadeSalas];
                    for (int i = 0; i < quantidadeSalas; i++) {
                        distribuicao[i] = quantidadeAlunosSala;
                        if(quantidadeAlunosNaoDivididos>0){
                            distribuicao[i]++;
                            quantidadeAlunosNaoDivididos--;
                        }
                    }
                    int cont = 0;
                    int totalAlunos = getEntitys().size();
                    for (int sala = 0; sala < quantidadeSalas; sala++) {
                        for (int j = 0; j < distribuicao[sala]; j++) {
                            Aluno aluno = getEntitys().get(cont);
                            aluno.setSala(sala+1);
                            aluno.setSession(s);
                            aluno.save();
                            cont++;
                        }
                    }
                }
            });
        } catch (BusinessException ex) {
            addMessage(getSeverityWarn(), ex.getMessage());
            Logger.getLogger(GerenciarAlunoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        int qtAlunos = 351;
        int qtSalas = 11;
        int qtAlunosSalasNaoDivididos = qtAlunos%qtSalas;
        int qtAlunosSalas = qtAlunos-qtAlunosSalasNaoDivididos;
        System.out.println("Total de alunos não divididos: "+qtAlunosSalasNaoDivididos);
        System.out.println("Total Alunos Divididos: "+qtAlunosSalas);
        System.out.println("Alunos por sala: "+qtAlunosSalas/qtSalas);
        int[] distribuicao = new int[qtSalas];
        for (int i = 0; i < qtSalas; i++) {
            distribuicao[i] = qtAlunosSalas/qtSalas;
            if(qtAlunosSalasNaoDivididos>0){
                distribuicao[i]++;
                qtAlunosSalasNaoDivididos--;
            }
            System.out.println("Sala "+(i+1)+" com  "+distribuicao[i]+" alunos.");
        }
        
        
    }

    public Integer getQuantidadeSalas() {
        return quantidadeSalas;
    }

    public void setQuantidadeSalas(Integer quantidadeSalas) {
        this.quantidadeSalas = quantidadeSalas;
    }
}
