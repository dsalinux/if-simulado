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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Danilo Souza Almeida
 */
@Entity
@Table(name = "acertos_turmas")
@NamedQueries({
    @NamedQuery(name = "AcertosTurmas.findAll", query = "SELECT a FROM AcertosTurmas a")})
public class AcertosTurmas extends EntityManageable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nome")
    private String nome;
    @Column(name = "quantidade_questeos_dia1")
    private Integer quantidadeQuesteosDia1;
    @Column(name = "quantidade_questeos_dia2")
    private Integer quantidadeQuesteosDia2;
    @Column(name = "quantidade_anuladas_dia1")
    private Integer quantidadeAnuladasDia1;
    @Column(name = "quantidade_anuladas_dia2")
    private Integer quantidadeAnuladasDia2;

    public AcertosTurmas() {
    }

    public AcertosTurmas(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadeQuesteosDia1() {
        return quantidadeQuesteosDia1;
    }

    public void setQuantidadeQuesteosDia1(Integer quantidadeQuesteosDia1) {
        this.quantidadeQuesteosDia1 = quantidadeQuesteosDia1;
    }

    public Integer getQuantidadeQuesteosDia2() {
        return quantidadeQuesteosDia2;
    }

    public void setQuantidadeQuesteosDia2(Integer quantidadeQuesteosDia2) {
        this.quantidadeQuesteosDia2 = quantidadeQuesteosDia2;
    }

    public Integer getQuantidadeAnuladasDia1() {
        return quantidadeAnuladasDia1;
    }

    public void setQuantidadeAnuladasDia1(Integer quantidadeAnuladasDia1) {
        this.quantidadeAnuladasDia1 = quantidadeAnuladasDia1;
    }

    public Integer getQuantidadeAnuladasDia2() {
        return quantidadeAnuladasDia2;
    }

    public void setQuantidadeAnuladasDia2(Integer quantidadeAnuladasDia2) {
        this.quantidadeAnuladasDia2 = quantidadeAnuladasDia2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nome != null ? nome.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcertosTurmas)) {
            return false;
        }
        AcertosTurmas other = (AcertosTurmas) object;
        if ((this.nome == null && other.nome != null) || (this.nome != null && !this.nome.equals(other.nome))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.ifnmg.arinos.entity.AcertosTurmas[ nome=" + nome + " ]";
    }

    @Override
    public void save() throws BusinessException {
        AcertosTurmas acertosTurmas = this;
        getSession().merge(acertosTurmas);
    }

    @Override
    public void remove() throws BusinessException {
        throw new BusinessException("Remoção de aluno está desativada.");
    }

    @Override
    public void valid() throws BusinessException {
    }
    
}
