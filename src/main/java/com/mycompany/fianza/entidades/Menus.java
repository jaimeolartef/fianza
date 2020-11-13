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
@Table(name = "menus")
@NamedQueries({
    @NamedQuery(name = "Menus.findAll", query = "SELECT m FROM Menus m"),
    @NamedQuery(name = "Menus.findById", query = "SELECT m FROM Menus m WHERE m.id = :id"),
    @NamedQuery(name = "Menus.findByNombre", query = "SELECT m FROM Menus m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Menus.findByCodigo", query = "SELECT m FROM Menus m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "Menus.findByIndHabilitado", query = "SELECT m FROM Menus m WHERE m.indHabilitado = :indHabilitado"),
    @NamedQuery(name = "Menus.findByUrl", query = "SELECT m FROM Menus m WHERE m.url = :url"),
    @NamedQuery(name = "Menus.findByNivel", query = "SELECT m FROM Menus m WHERE m.nivel = :nivel")})
public class Menus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @Size(max = 200)
    @Column(name = "Url")
    private String url;
    @Size(max = 20)
    @Column(name = "Nivel")
    private String nivel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMenu", fetch = FetchType.LAZY)
    private Set<Permisos> permisosSet;

    public Menus() {
    }

    public Menus(Integer id) {
        this.id = id;
    }

    public Menus(Integer id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getIndHabilitado() {
        return indHabilitado;
    }

    public void setIndHabilitado(Boolean indHabilitado) {
        this.indHabilitado = indHabilitado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Set<Permisos> getPermisosSet() {
        return permisosSet;
    }

    public void setPermisosSet(Set<Permisos> permisosSet) {
        this.permisosSet = permisosSet;
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
        if (!(object instanceof Menus)) {
            return false;
        }
        Menus other = (Menus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Menus[ id=" + id + " ]";
    }
    
}
