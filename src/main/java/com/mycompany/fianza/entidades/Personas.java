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
@Table(name = "personas")
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findById", query = "SELECT p FROM Personas p WHERE p.id = :id"),
    @NamedQuery(name = "Personas.findByPrimerNombre", query = "SELECT p FROM Personas p WHERE p.primerNombre = :primerNombre"),
    @NamedQuery(name = "Personas.findBySegundoNombre", query = "SELECT p FROM Personas p WHERE p.segundoNombre = :segundoNombre"),
    @NamedQuery(name = "Personas.findByPrimerApellido", query = "SELECT p FROM Personas p WHERE p.primerApellido = :primerApellido"),
    @NamedQuery(name = "Personas.findBySegundoApellido", query = "SELECT p FROM Personas p WHERE p.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Personas.findByIdentificacion", query = "SELECT p FROM Personas p WHERE p.identificacion = :identificacion"),
    @NamedQuery(name = "Personas.findByTelefono", query = "SELECT p FROM Personas p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Personas.findByCoreo", query = "SELECT p FROM Personas p WHERE p.coreo = :coreo"),
    @NamedQuery(name = "Personas.findByIndHabilitado", query = "SELECT p FROM Personas p WHERE p.indHabilitado = :indHabilitado")})
public class Personas implements Serializable {

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
    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 50)
    @Column(name = "coreo")
    private String coreo;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @JoinColumn(name = "tipo_documento", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ListasGrupo tipoDocumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersona", fetch = FetchType.LAZY)
    private Set<Inmuebles> inmueblesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersona", fetch = FetchType.LAZY)
    private Set<Documentos> documentosSet;

    public Personas() {
    }

    public Personas(Integer id) {
        this.id = id;
    }

    public Personas(Integer id, String primerNombre, String primerApellido, String identificacion) {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCoreo() {
        return coreo;
    }

    public void setCoreo(String coreo) {
        this.coreo = coreo;
    }

    public Boolean getIndHabilitado() {
        return indHabilitado;
    }

    public void setIndHabilitado(Boolean indHabilitado) {
        this.indHabilitado = indHabilitado;
    }

    public ListasGrupo getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(ListasGrupo tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Set<Inmuebles> getInmueblesSet() {
        return inmueblesSet;
    }

    public void setInmueblesSet(Set<Inmuebles> inmueblesSet) {
        this.inmueblesSet = inmueblesSet;
    }

    public Set<Documentos> getDocumentosSet() {
        return documentosSet;
    }

    public void setDocumentosSet(Set<Documentos> documentosSet) {
        this.documentosSet = documentosSet;
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
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Personas[ id=" + id + " ]";
    }
    
}
