/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dianaplata
 */
@Entity
@Table(name = "listas")
@NamedQueries({
    @NamedQuery(name = "Listas.findAll", query = "SELECT l FROM Listas l"),
    @NamedQuery(name = "Listas.findById", query = "SELECT l FROM Listas l WHERE l.id = :id"),
    @NamedQuery(name = "Listas.findByCodigo", query = "SELECT l FROM Listas l WHERE l.codigo = :codigo"),
    @NamedQuery(name = "Listas.findByDescripcion", query = "SELECT l FROM Listas l WHERE l.descripcion = :descripcion"),
    @NamedQuery(name = "Listas.findByIndHabilitado", query = "SELECT l FROM Listas l WHERE l.indHabilitado = :indHabilitado")})
public class Listas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @JoinColumn(name = "id_listasgrupo", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ListasGrupo idListasgrupo;

    public Listas() {
    }

    public Listas(Integer id) {
        this.id = id;
    }

    public Listas(Integer id, String codigo, String descripcion) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getIndHabilitado() {
        return indHabilitado;
    }

    public void setIndHabilitado(Boolean indHabilitado) {
        this.indHabilitado = indHabilitado;
    }

    public ListasGrupo getIdListasgrupo() {
        return idListasgrupo;
    }

    public void setIdListasgrupo(ListasGrupo idListasgrupo) {
        this.idListasgrupo = idListasgrupo;
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
        if (!(object instanceof Listas)) {
            return false;
        }
        Listas other = (Listas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Listas[ id=" + id + " ]";
    }
    
}
