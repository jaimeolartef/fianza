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
@Table(name = "permisos")
@NamedQueries({
    @NamedQuery(name = "Permisos.findAll", query = "SELECT p FROM Permisos p"),
    @NamedQuery(name = "Permisos.findById", query = "SELECT p FROM Permisos p WHERE p.id = :id"),
    @NamedQuery(name = "Permisos.findByIndHabilitado", query = "SELECT p FROM Permisos p WHERE p.indHabilitado = :indHabilitado"),
    @NamedQuery(name = "Permisos.findByConsultar", query = "SELECT p FROM Permisos p WHERE p.consultar = :consultar"),
    @NamedQuery(name = "Permisos.findByCrear", query = "SELECT p FROM Permisos p WHERE p.crear = :crear"),
    @NamedQuery(name = "Permisos.findByEditar", query = "SELECT p FROM Permisos p WHERE p.editar = :editar"),
    @NamedQuery(name = "Permisos.findByEliminar", query = "SELECT p FROM Permisos p WHERE p.eliminar = :eliminar")})
public class Permisos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @Column(name = "consultar")
    private Boolean consultar;
    @Column(name = "crear")
    private Boolean crear;
    @Column(name = "editar")
    private Boolean editar;
    @Column(name = "eliminar")
    private Boolean eliminar;
    @JoinColumn(name = "id_menu", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Menus idMenu;
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Roles idRol;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuarios idUsuario;

    public Permisos() {
    }

    public Permisos(Integer id) {
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

    public Boolean getConsultar() {
        return consultar;
    }

    public void setConsultar(Boolean consultar) {
        this.consultar = consultar;
    }

    public Boolean getCrear() {
        return crear;
    }

    public void setCrear(Boolean crear) {
        this.crear = crear;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Menus getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Menus idMenu) {
        this.idMenu = idMenu;
    }

    public Roles getIdRol() {
        return idRol;
    }

    public void setIdRol(Roles idRol) {
        this.idRol = idRol;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof Permisos)) {
            return false;
        }
        Permisos other = (Permisos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Permisos[ id=" + id + " ]";
    }
    
}
