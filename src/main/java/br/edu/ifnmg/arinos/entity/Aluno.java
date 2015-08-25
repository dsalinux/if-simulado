/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.arinos.entity;

import br.edu.ifnmg.arinos.util.exception.BusinessException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Danilo Souza Almeida
 */
@Entity
@Table(name = "aluno")
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a")})
public class Aluno extends EntityManageable implements Serializable, Comparable<Aluno> {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nome")
    private String nome;
    @ManyToOne(optional = false)
    @JoinColumn(name = "turma", referencedColumnName = "nome")
    private AcertosTurmas turma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "curso")
    private String curso;
    @Column(name = "quantidade_acertos_1")
    private Integer quantidadeAcertos1;
    @Column(name = "quantidade_acertos_2")
    private Integer quantidadeAcertos2;
    @Column(name = "sala")
    private Integer sala;
    @Column(name = "nota_dia_1")
    private BigDecimal notaAlunoDia1;
    @Column(name = "nota_dia_2")
    private BigDecimal notaAlunoDia2;

    public Aluno() {
    }

    public Aluno(Integer id) {
        this.id = id;
    }

    public Aluno(Integer id, String nome, String curso) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AcertosTurmas getTurma() {
        return turma;
    }

    public void setTurma(AcertosTurmas turma) {
        this.turma = turma;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getQuantidadeAcertos1() {
        return quantidadeAcertos1;
    }

    public void setQuantidadeAcertos1(Integer quantidadeAcertos1) {
        this.quantidadeAcertos1 = quantidadeAcertos1;
    }
    public Integer getQuantidadeAcertos2() {
        return quantidadeAcertos2;
    }

    public void setQuantidadeAcertos2(Integer quantidadeAcertos2) {
        this.quantidadeAcertos2 = quantidadeAcertos2;
    }

    public Integer getSala() {
        return sala;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public BigDecimal getnotaAlunoDia1() {
        return notaAlunoDia1;
    }

    public void setNotaAlunoDia1(BigDecimal notaAlunoDia1) {
        this.notaAlunoDia1 = notaAlunoDia1;
    }
    
    public BigDecimal getnotaAlunoDia2() {
        return notaAlunoDia2;
    }

    public void setNotaAlunoDia2(BigDecimal notaAlunoDia2) {
        this.notaAlunoDia2 = notaAlunoDia2;
    }
    
    public String getNotaDia1(){
        Float total = 0f;
        if(quantidadeAcertos1 != null && quantidadeAcertos1.compareTo(0) == 1){
            Float totalAcertos = quantidadeAcertos1.floatValue();
            Float totalQuestoes = turma.getQuantidadeQuesteosDia1().floatValue();
            if(quantidadeAcertos2 != null && quantidadeAcertos2.compareTo(0) == 1){
                totalAcertos+= quantidadeAcertos2.floatValue();
                totalQuestoes+=turma.getQuantidadeQuesteosDia2().floatValue();
            }
            if(turma.getQuantidadeAnuladasDia1() != null && turma.getQuantidadeAnuladasDia1().compareTo(0) == 1){
                totalAcertos += turma.getQuantidadeAnuladasDia1();
            }
            if(turma.getQuantidadeAnuladasDia2() != null && turma.getQuantidadeAnuladasDia2().compareTo(0) == 1){
                totalAcertos += turma.getQuantidadeAnuladasDia2();
            }
            total = (totalAcertos * 5)/totalQuestoes;
        }
        setNotaAlunoDia1(new BigDecimal(total));
        return new DecimalFormat("0.00").format(total);
    }
    public String getNotaDia2(){
        Float total = 0f;
        if(quantidadeAcertos2 != null && quantidadeAcertos2.compareTo(0) == 1){
            Float totalAcertos = quantidadeAcertos2.floatValue();
            Float totalQuestoes = turma.getQuantidadeQuesteosDia1().floatValue();
            if(quantidadeAcertos1 != null && quantidadeAcertos1.compareTo(0) == 1){
                totalAcertos+= quantidadeAcertos1.floatValue();
                totalQuestoes+=turma.getQuantidadeQuesteosDia2().floatValue();
            }
            if(turma.getQuantidadeAnuladasDia1() != null && turma.getQuantidadeAnuladasDia1().compareTo(0) == 1){
                totalAcertos += turma.getQuantidadeAnuladasDia1();
            }
            if(turma.getQuantidadeAnuladasDia2() != null && turma.getQuantidadeAnuladasDia2().compareTo(0) == 1){
                totalAcertos += turma.getQuantidadeAnuladasDia2();
            }
            total = (totalAcertos * 5)/totalQuestoes;
        }
        setNotaAlunoDia2(new BigDecimal(total));
        return new DecimalFormat("0.00").format(total);
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
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.ifnmg.arinos.entity.Aluno[ id=" + id + " ]";
    }

    @Override
    public void save() throws BusinessException {
        Aluno aluno = this;
        aluno = (Aluno) getSession().merge(aluno);
        setId(aluno.getId());
    }

    @Override
    public void remove() throws BusinessException {
        throw new BusinessException("Remoção de aluno está desativada.");
    }

    @Override
    public void valid() throws BusinessException {
        
    }

    @Override
    public int compareTo(Aluno o) {
        return nome.compareToIgnoreCase(o.nome);
    }
    
}
