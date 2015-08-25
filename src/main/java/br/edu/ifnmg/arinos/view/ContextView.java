package br.edu.ifnmg.arinos.view;

import br.edu.ifnmg.arinos.entity.Configuracoes;
import br.edu.ifnmg.arinos.entity.Usuario;
import br.edu.ifnmg.arinos.util.Constants;
import br.edu.ifnmg.arinos.util.Context;
import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * @author danilo
 */
@RequestScoped
@ManagedBean
public class ContextView extends GenericBean {

    public String getMensagemLogado() {
        String mensagem = "";
        if (Context.getLogin() != null) {
            int hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (hora >= 0 && hora < 12) {
                mensagem = "Bom dia ";
            } else if (hora >= 12 && hora < 18) {
                mensagem = "Boa tarde ";
            } else {
                mensagem = "Boa noite ";
            }
            String nome = Context.getLogin().getNome();
            nome = nome.split(" ")[0];
            mensagem += nome;
        }
        return mensagem;
    }

    public Usuario getLogin() {
        return Context.getLogin();
    }

    public void setLogin(Usuario login) {
        Context.setLogin(login);
    }
    
    public void deslogar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            Context.setLogin(null);
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
        } catch (IOException ex) {
            Logger.getLogger(ContextView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getVersion(){
        return Constants.CURRENT_VERSION;
    }
    
    public Configuracoes getConfiguracoes(){
        try {
            return Context.getConfiguracoes();
        } catch (BusinessException ex) {
            Logger.getLogger(ContextView.class.getName()).log(Level.SEVERE, null, ex);
            addMessage(getSeverityWarn(), ex.getMessage());
            return null;
        }
    }
}
