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
 * @author dianaplata
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findById", query = "SELECT u FROM Usuarios u WHERE u.id = :id"),
    @NamedQuery(name = "Usuarios.findByPrimerNombre", query = "SELECT u FROM Usuarios u WHERE u.primerNombre = :primerNombre"),
    @NamedQuery(name = "Usuarios.findBySegundoNombre", query = "SELECT u FROM Usuarios u WHERE u.segundoNombre = :segundoNombre"),
    @NamedQuery(name = "Usuarios.findByPrimerApellido", query = "SELECT u FROM Usuarios u WHERE u.primerApellido = :primerApellido"),
    @NamedQuery(name = "Usuarios.findBySegundoApellido", query = "SELECT u FROM Usuarios u WHERE u.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Usuarios.findByContrasena", query = "SELECT u FROM Usuarios u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuarios.findByCodigo", query = "SELECT u FROM Usuarios u WHERE u.codigo = :codigo"),
    @NamedQuery(name = "Usuarios.findByIndHabilitado", query = "SELECT u FROM Usuarios u WHERE u.indHabilitado = :indHabilitado"),
    @NamedQuery(name = "Usuarios.findByIndBloqueo", query = "SELECT u FROM Usuarios u WHERE u.indBloqueo = :indBloqueo"),
    @NamedQuery(name = "Usuarios.findByIdentificacion", query = "SELECT u FROM Usuarios u WHERE u.identificacion = :identificacion")})
public class Usuarios implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "primer_nombre")
    private String primerNombre;
    @Size(max = 20)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Size(max = 20)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Size(max = 70)
    @Column(name = "contrasena")
    private String contrasena;
    @Size(max = 50)
    @Column(name = "correo")
    private String correo;
    @Size(max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @Column(name = "ind_bloqueo")
    private Boolean indBloqueo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<Permisos> permisosSet;
    @JoinColumn(name = "id_inmobiliaria", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inmobiliarias idInmobiliaria;
    @JoinColumn(name = "tipodocumento", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ListasGrupo tipodocumento;
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Roles idRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<Promotores> promotoresSet;

    public Usuarios() {
    }

    public Usuarios(Integer id) {
        this.id = id;
    }

    public Usuarios(Integer id, String primerNombre, String primerApellido, String identificacion) {
        this.id = id;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.identificacion = identificacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }


    public Boolean getIndHabilitado() {
        return indHabilitado;
    }

    public void setIndHabilitado(Boolean indHabilitado) {
        this.indHabilitado = indHabilitado;
    }

    public Boolean getIndBloqueo() {
        return indBloqueo;
    }

    public void setIndBloqueo(Boolean indBloqueo) {
        this.indBloqueo = indBloqueo;
    }


    public Set<Permisos> getPermisosSet() {
        return permisosSet;
    }

    public void setPermisosSet(Set<Permisos> permisosSet) {
        this.permisosSet = permisosSet;
    }

    public Inmobiliarias getIdInmobiliaria() {
        return idInmobiliaria;
    }

    public void setIdInmobiliaria(Inmobiliarias idInmobiliaria) {
        this.idInmobiliaria = idInmobiliaria;
    }

    public ListasGrupo getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(ListasGrupo tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public Roles getIdRol() {
        return idRol;
    }

    public void setIdRol(Roles idRol) {
        this.idRol = idRol;
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
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Usuarios[ id=" + id + " ]";
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
}
