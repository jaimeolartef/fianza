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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dianaplata
 */
@Entity
@Table(name = "inmobiliarias")
@NamedQueries({
    @NamedQuery(name = "Inmobiliarias.findAll", query = "SELECT i FROM Inmobiliarias i"),
    @NamedQuery(name = "Inmobiliarias.findById", query = "SELECT i FROM Inmobiliarias i WHERE i.id = :id"),
    @NamedQuery(name = "Inmobiliarias.findByNombre", query = "SELECT i FROM Inmobiliarias i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Inmobiliarias.findByNit", query = "SELECT i FROM Inmobiliarias i WHERE i.nit = :nit"),
    @NamedQuery(name = "Inmobiliarias.findByDireccion", query = "SELECT i FROM Inmobiliarias i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "Inmobiliarias.findByTelefono", query = "SELECT i FROM Inmobiliarias i WHERE i.telefono = :telefono"),
    @NamedQuery(name = "Inmobiliarias.findByIndHabilitado", query = "SELECT i FROM Inmobiliarias i WHERE i.indHabilitado = :indHabilitado")})
public class Inmobiliarias implements Serializable {

    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nit")
    private String nit;
    @Size(max = 60)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 30)
    @Column(name = "telefono")
    private String telefono;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInmobiliaria", fetch = FetchType.LAZY)
    private Set<DetallePersonas> detallePersonasSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInmobiliaria", fetch = FetchType.LAZY)
    private Set<Usuarios> usuariosSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInmobiliaria", fetch = FetchType.LAZY)
    private Set<Inmuebles> inmueblesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInmobiliaria", fetch = FetchType.LAZY)
    private Set<Solicitudes> solicitudesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInmobiliaria", fetch = FetchType.LAZY)
    private Set<Promotores> promotoresSet;

    public Inmobiliarias() {
    }

    public Inmobiliarias(Integer id) {
        this.id = id;
    }

    public Inmobiliarias(Integer id, String nit) {
        this.id = id;
        this.nit = nit;
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

    public Set<Usuarios> getUsuariosSet() {
        return usuariosSet;
    }

    public void setUsuariosSet(Set<Usuarios> usuariosSet) {
        this.usuariosSet = usuariosSet;
    }

    public Set<Inmuebles> getInmueblesSet() {
        return inmueblesSet;
    }

    public void setInmueblesSet(Set<Inmuebles> inmueblesSet) {
        this.inmueblesSet = inmueblesSet;
    }

    public Set<Solicitudes> getSolicitudesSet() {
        return solicitudesSet;
    }

    public void setSolicitudesSet(Set<Solicitudes> solicitudesSet) {
        this.solicitudesSet = solicitudesSet;
    }

    public Set<Promotores> getPromotoresSet() {
        return promotoresSet;
    }

    public void setPromotoresSet(Set<Promotores> promotoresSet) {
        this.promotoresSet = promotoresSet;
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
        if (!(object instanceof Inmobiliarias)) {
            return false;
        }
        Inmobiliarias other = (Inmobiliarias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Inmobiliarias[ id=" + id + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
