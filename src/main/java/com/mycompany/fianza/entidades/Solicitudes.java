/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author dianaplata
 */
@Entity
@Table(name = "solicitudes")
@NamedQueries({
    @NamedQuery(name = "Solicitudes.findAll", query = "SELECT s FROM Solicitudes s"),
    @NamedQuery(name = "Solicitudes.findById", query = "SELECT s FROM Solicitudes s WHERE s.id = :id"),
    @NamedQuery(name = "Solicitudes.findByFecha", query = "SELECT s FROM Solicitudes s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "Solicitudes.findByContrato", query = "SELECT s FROM Solicitudes s WHERE s.contrato = :contrato"),
    @NamedQuery(name = "Solicitudes.findByObservaciones", query = "SELECT s FROM Solicitudes s WHERE s.observaciones = :observaciones"),
    @NamedQuery(name = "Solicitudes.findByValorEstudio", query = "SELECT s FROM Solicitudes s WHERE s.valorEstudio = :valorEstudio"),
    @NamedQuery(name = "Solicitudes.findByValorIva", query = "SELECT s FROM Solicitudes s WHERE s.valorIva = :valorIva"),
    @NamedQuery(name = "Solicitudes.findByTotalPago", query = "SELECT s FROM Solicitudes s WHERE s.totalPago = :totalPago"),
    @NamedQuery(name = "Solicitudes.findByIndHabilitado", query = "SELECT s FROM Solicitudes s WHERE s.indHabilitado = :indHabilitado")})
public class Solicitudes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 50)
    @Column(name = "contrato")
    private String contrato;
    @Size(max = 200)
    @Column(name = "observaciones")
    private String observaciones;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_estudio")
    private BigDecimal valorEstudio;
    @Column(name = "valor_iva")
    private BigDecimal valorIva;
    @Column(name = "total_pago")
    private BigDecimal totalPago;
    @Column(name = "ind_habilitado")
    private Boolean indHabilitado;
    @JoinColumn(name = "id_inmobiliaria", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inmobiliarias idInmobiliaria;
    @JoinColumn(name = "tipo_inmueble", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ListasGrupo tipoInmueble;

    public Solicitudes() {
    }

    public Solicitudes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getValorEstudio() {
        return valorEstudio;
    }

    public void setValorEstudio(BigDecimal valorEstudio) {
        this.valorEstudio = valorEstudio;
    }

    public BigDecimal getValorIva() {
        return valorIva;
    }

    public void setValorIva(BigDecimal valorIva) {
        this.valorIva = valorIva;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
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

    public ListasGrupo getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(ListasGrupo tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
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
        if (!(object instanceof Solicitudes)) {
            return false;
        }
        Solicitudes other = (Solicitudes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Solicitudes[ id=" + id + " ]";
    }
    
}
