/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.arinos.entity;

import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Danilo Souza Almeida
 */
@Entity
@Table(name = "configuracoes")
public class Configuracoes extends EntityManageable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dia1")
    private boolean dia1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dia2")
    private boolean dia2;

    public Configuracoes() {
    }

    public Configuracoes(Integer id) {
        this.id = id;
    }

    public Configuracoes(Integer id, boolean dia1, boolean dia2) {
        this.id = id;
        this.dia1 = dia1;
        this.dia2 = dia2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getDia1() {
        return dia1;
    }

    public void setDia1(boolean dia1) {
        this.dia1 = dia1;
    }

    public boolean getDia2() {
        return dia2;
    }

    public void setDia2(boolean dia2) {
        this.dia2 = dia2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracoes)) {
            return false;
        }
        Configuracoes other = (Configuracoes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.ifnmg.arinos.entity.Configuracoes[ id=" + id + " ]";
    }

    @Override
    public void save() throws BusinessException {
        Configuracoes configuracoes = this;
        getSession().merge(configuracoes);
    }

    @Override
    public void remove() throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valid() throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
