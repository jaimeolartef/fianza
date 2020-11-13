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
@Table(name = "promotores")
@NamedQueries({
    @NamedQuery(name = "Promotores.findAll", query = "SELECT p FROM Promotores p"),
    @NamedQuery(name = "Promotores.findById", query = "SELECT p FROM Promotores p WHERE p.id = :id"),
    @NamedQuery(name = "Promotores.findByPrimerNombre", query = "SELECT p FROM Promotores p WHERE p.primerNombre = :primerNombre"),
    @NamedQuery(name = "Promotores.findBySegundoNombre", query = "SELECT p FROM Promotores p WHERE p.segundoNombre = :segundoNombre"),
    @NamedQuery(name = "Promotores.findByPrimerApellido", query = "SELECT p FROM Promotores p WHERE p.primerApellido = :primerApellido"),
    @NamedQuery(name = "Promotores.findBySegundoApellido", query = "SELECT p FROM Promotores p WHERE p.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Promotores.findByIdentificacion", query = "SELECT p FROM Promotores p WHERE p.identificacion = :identificacion"),
    @NamedQuery(name = "Promotores.findByDireccion", query = "SELECT p FROM Promotores p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Promotores.findByTelefono", query = "SELECT p FROM Promotores p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Promotores.findByCorreo", query = "SELECT p FROM Promotores p WHERE p.correo = :correo"),
    @NamedQuery(name = "Promotores.findByIndHabilitado", query = "SELECT p FROM Promotores p WHERE p.indHabilitado = :indHabilitado")})
public class Promotores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 60)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 50)
    @Column(name = "correo")
    private String correo;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @JoinColumn(name = "id_inmobiliaria", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inmobiliarias idInmobiliaria;
    @JoinColumn(name = "tipo_documento", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ListasGrupo tipoDocumento;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuarios idUsuario;

    public Promotores() {
    }

    public Promotores(Integer id) {
        this.id = id;
    }

    public Promotores(Integer id, String primerNombre, String primerApellido, String identificacion) {
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public ListasGrupo getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(ListasGrupo tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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
        if (!(object instanceof Promotores)) {
            return false;
        }
        Promotores other = (Promotores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Promotores[ id=" + id + " ]";
    }
    
}
