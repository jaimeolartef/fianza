/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.entidades;

/**
 *
 * @author dianaplata
 */
public class UsuarioSession {

    private String usuario;
    private Integer rol;
    private Usuarios usuarioLogeado;
    private String modulo;

    public UsuarioSession(String usuario, Integer rol, Usuarios usuarioLogeado) {
        this.usuario = usuario;
        this.rol = rol;
        this.usuarioLogeado = usuarioLogeado;
    }

    public UsuarioSession(String modulo) {
        this.modulo = modulo;
    }

    public UsuarioSession(Usuarios usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }
    
    public UsuarioSession() {
    }
   
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }    

    public Usuarios getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuarios usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
}
