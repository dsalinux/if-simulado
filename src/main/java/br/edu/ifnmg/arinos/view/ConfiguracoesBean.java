/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.arinos.view;

import br.edu.ifnmg.arinos.entity.Configuracoes;
import br.edu.ifnmg.arinos.util.Context;
import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

/**
 *
 * @author Danilo Souza Almeida
 */
@ManagedBean
@SessionScoped
public class ConfiguracoesBean extends AbstractManager {
    
    private Configuracoes configuracoes;
    
    @PostConstruct
    public void iniciarlizar(){
        try {
            configuracoes = Context.getConfiguracoes();
        } catch (BusinessException ex) {
            Logger.getLogger(ConfiguracoesBean.class.getName()).log(Level.SEVERE, null, ex);
            addMessage(getSeverityWarn(), ex.getMessage());
        }
    }

    public void save(){
        try {
            doInTransaction(new PersistenceActionWithoutResult() {
                @Override
                public void execute(Session s) throws BusinessException {
                    configuracoes.setSession(s);
                    configuracoes.save();
                    addMessage("Salvo com sucesso!");
                }
            });
        } catch (BusinessException ex) {
            Logger.getLogger(ConfiguracoesBean.class.getName()).log(Level.SEVERE, null, ex);
            addMessage(getSeverityWarn(), ex.getMessage());
        }
    }
    
    public Configuracoes getConfiguracoes() {
        return configuracoes;
    }
    public void setConfiguracoes(Configuracoes configuracoes) {
        this.configuracoes = configuracoes;
    }
    
}
