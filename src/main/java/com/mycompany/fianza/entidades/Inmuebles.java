/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.entidades;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;

/**
 *
 * @author dianaplata
 */
@Entity
@Table(name = "inmuebles")
@NamedQueries({
    @NamedQuery(name = "Inmuebles.findAll", query = "SELECT i FROM Inmuebles i"),
    @NamedQuery(name = "Inmuebles.findById", query = "SELECT i FROM Inmuebles i WHERE i.id = :id"),
    @NamedQuery(name = "Inmuebles.findByDescripcion", query = "SELECT i FROM Inmuebles i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Inmuebles.findByArea", query = "SELECT i FROM Inmuebles i WHERE i.area = :area"),
    @NamedQuery(name = "Inmuebles.findByDireccion", query = "SELECT i FROM Inmuebles i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "Inmuebles.findByValorCanon", query = "SELECT i FROM Inmuebles i WHERE i.valorCanon = :valorCanon"),
    @NamedQuery(name = "Inmuebles.findByIndHabilitado", query = "SELECT i FROM Inmuebles i WHERE i.indHabilitado = :indHabilitado")})
public class Inmuebles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 10)
    @Column(name = "area")
    private String area;
    @Size(max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "valor_canon")
    private BigInteger valorCanon;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @JoinColumn(name = "id_inmobiliaria", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inmobiliarias idInmobiliaria;
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Personas idPersona;

    public Inmuebles() {
    }

    public Inmuebles(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigInteger getValorCanon() {
        return valorCanon;
    }

    public void setValorCanon(BigInteger valorCanon) {
        this.valorCanon = valorCanon;
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

    public Personas getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Personas idPersona) {
        this.idPersona = idPersona;
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
        if (!(object instanceof Inmuebles)) {
            return false;
        }
        Inmuebles other = (Inmuebles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Inmuebles[ id=" + id + " ]";
    }
    
}
