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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author dianaplata
 */
@Entity
@Table(name = "constantes")
@NamedQueries({
    @NamedQuery(name = "Constantes.findAll", query = "SELECT c FROM Constantes c"),
    @NamedQuery(name = "Constantes.findById", query = "SELECT c FROM Constantes c WHERE c.id = :id"),
    @NamedQuery(name = "Constantes.findByCodigo", query = "SELECT c FROM Constantes c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Constantes.findByDescripcion", query = "SELECT c FROM Constantes c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Constantes.findByValor", query = "SELECT c FROM Constantes c WHERE c.valor = :valor")})
public class Constantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "valor")
    private String valor;

    public Constantes() {
    }

    public Constantes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
        if (!(object instanceof Constantes)) {
            return false;
        }
        Constantes other = (Constantes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.fianza.entidades.Constantes[ id=" + id + " ]";
    }
    
}
