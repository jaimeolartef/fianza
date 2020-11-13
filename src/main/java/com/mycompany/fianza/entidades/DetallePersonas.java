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

/**
 *
 * @author dianaplata
 */
@Entity
@Table(name = "detalle_personas")
@NamedQueries({
    @NamedQuery(name = "DetallePersonas.findAll", query = "SELECT d FROM DetallePersonas d"),
    @NamedQuery(name = "DetallePersonas.findById", query = "SELECT d FROM DetallePersonas d WHERE d.id = :id"),
    @NamedQuery(name = "DetallePersonas.findByIndHabilitado", query = "SELECT d FROM DetallePersonas d WHERE d.indHabilitado = :indHabilitado")})
public class DetallePersonas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @JoinColumn(name = "id_inmobiliaria", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inmobiliarias idInmobiliaria;
    @JoinColumn(name = "estado", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ListasGrupo estado;
    @JoinColumn(name = "tipo_persona", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ListasGrupo tipoPersona;

    public DetallePersonas() {
    }

    public DetallePersonas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIndHabilitado() {
        return indHabilitado;
    }

    public void setIndHabilitado(Boolean indHabilitado) {
        this.indHabilitado = indHabilitado;
    }

    public Inmobiliarias getIdInmobiliaria() {
        return idInmobiliaria;
    }

    public void setIdInmobiliaria(Inmobiliarias idInmobiliaria) {
        this.idInmobiliaria = idInmobiliaria;
    }

    public ListasGrupo getEstado() {
        return estado;
    }

    public void setEstado(ListasGrupo estado) {
        this.estado = estado;
    }

    public ListasGrupo getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(ListasGrupo tipoPersona) {
        this.tipoPersona = tipoPersona;
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
        if (!(object instanceof DetallePersonas)) {
            return false;
        }
        DetallePersonas other = (DetallePersonas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.DetallePersonas[ id=" + id + " ]";
    }
    
}
