package br.edu.ifnmg.arinos.view;

import br.edu.ifnmg.arinos.entity.Usuario;
import br.edu.ifnmg.arinos.util.StringHelper;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class GerenciarLoginView extends GenericCrudBean<Usuario> {
 
    private String senha;

    @Override
    public void save(ActionEvent actionEvent) {
        if(!StringHelper.isEmpty(senha)){
            getEntity().setSenha(senha);
        }
        super.save(actionEvent);
    }
    
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

}
