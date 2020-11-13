/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.entidades;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author dianaplata
 */
@Entity
@Table(name = "listas_grupo")
@NamedQueries({
    @NamedQuery(name = "ListasGrupo.findAll", query = "SELECT l FROM ListasGrupo l"),
    @NamedQuery(name = "ListasGrupo.findById", query = "SELECT l FROM ListasGrupo l WHERE l.id = :id"),
    @NamedQuery(name = "ListasGrupo.findByDescripcion", query = "SELECT l FROM ListasGrupo l WHERE l.descripcion = :descripcion"),
    @NamedQuery(name = "ListasGrupo.findByCodigo", query = "SELECT l FROM ListasGrupo l WHERE l.codigo = :codigo"),
    @NamedQuery(name = "ListasGrupo.findByIndHabilitado", query = "SELECT l FROM ListasGrupo l WHERE l.indHabilitado = :indHabilitado")})
public class ListasGrupo implements Serializable {

    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 10)
    @Column(name = "codigo")
    private String codigo;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado", fetch = FetchType.LAZY)
    private Set<DetallePersonas> detallePersonasSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPersona", fetch = FetchType.LAZY)
    private Set<DetallePersonas> detallePersonasSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipodocumento", fetch = FetchType.LAZY)
    private Set<Usuarios> usuariosSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumento", fetch = FetchType.LAZY)
    private Set<Personas> personasSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoInmueble", fetch = FetchType.LAZY)
    private Set<Solicitudes> solicitudesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumento", fetch = FetchType.LAZY)
    private Set<Documentos> documentosSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumento", fetch = FetchType.LAZY)
    private Set<Promotores> promotoresSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idListasgrupo", fetch = FetchType.LAZY)
    private Set<Listas> listasSet;

    public ListasGrupo() {
    }

    public ListasGrupo(Integer id) {
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

    public Set<DetallePersonas> getDetallePersonasSet() {
        return detallePersonasSet;
    }

    public void setDetallePersonasSet(Set<DetallePersonas> detallePersonasSet) {
        this.detallePersonasSet = detallePersonasSet;
    }

    public Set<DetallePersonas> getDetallePersonasSet1() {
        return detallePersonasSet1;
    }

    public void setDetallePersonasSet1(Set<DetallePersonas> detallePersonasSet1) {
        this.detallePersonasSet1 = detallePersonasSet1;
    }

    public Set<Usuarios> getUsuariosSet() {
        return usuariosSet;
    }

    public void setUsuariosSet(Set<Usuarios> usuariosSet) {
        this.usuariosSet = usuariosSet;
    }

    public Set<Personas> getPersonasSet() {
        return personasSet;
    }

    public void setPersonasSet(Set<Personas> personasSet) {
        this.personasSet = personasSet;
    }

    public Set<Solicitudes> getSolicitudesSet() {
        return solicitudesSet;
    }

    public void setSolicitudesSet(Set<Solicitudes> solicitudesSet) {
        this.solicitudesSet = solicitudesSet;
    }

    public Set<Documentos> getDocumentosSet() {
        return documentosSet;
    }

    public void setDocumentosSet(Set<Documentos> documentosSet) {
        this.documentosSet = documentosSet;
    }

    public Set<Promotores> getPromotoresSet() {
        return promotoresSet;
    }

    public void setPromotoresSet(Set<Promotores> promotoresSet) {
        this.promotoresSet = promotoresSet;
    }

    public Set<Listas> getListasSet() {
        return listasSet;
    }

    public void setListasSet(Set<Listas> listasSet) {
        this.listasSet = listasSet;
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
        if (!(object instanceof ListasGrupo)) {
            return false;
        }
        ListasGrupo other = (ListasGrupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.ListasGrupo[ id=" + id + " ]";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
}
