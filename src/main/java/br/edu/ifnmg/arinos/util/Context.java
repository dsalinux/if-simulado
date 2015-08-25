/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.arinos.util;

import br.edu.ifnmg.arinos.entity.Configuracoes;
import br.edu.ifnmg.arinos.entity.Usuario;
import br.edu.ifnmg.arinos.util.exception.BusinessException;
import br.edu.ifnmg.arinos.view.AbstractManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.hibernate.Session;

/**
 *
 * @author danilo
 */
public class Context {
    
    public static final String USUARIO_LOGADO = "usuario_logado";
    
    public static Usuario getLogin(){
        Object l = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USUARIO_LOGADO);
        if(l == null){
            return null;
        }
        Usuario login = (Usuario) l;
        return login;
    }
    public static void setLogin(Usuario login){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(USUARIO_LOGADO, login);
    }
    
    public static Configuracoes getConfiguracoes() throws BusinessException{
        AbstractManager am = new AbstractManager() {};
        return am.doInTransaction(new AbstractManager.PersistenceAction<Configuracoes>() {
            @Override
            public Configuracoes execute(Session s) throws BusinessException {
                Configuracoes configuracoes = (Configuracoes) s.get(Configuracoes.class, 1);
                if(configuracoes == null){
                    configuracoes = new Configuracoes(1, false, false);
                    configuracoes.setSession(s);
                    configuracoes.save();
                }
                return configuracoes;
            }
        });
    }
    

}
