/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import com.mycompany.fianza.entidades.UsuarioSession;
import com.mycompany.fianza.entidades.Usuarios;
import com.mycompany.fianza.facade.UsuariosJpaController;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author dianaplata
 */
public class CambiarClaveFormBean implements Serializable {

    private String priclave;
    private String confClave;
    private String actualClave;
    private Usuarios usuario;
    @EJB
    private UsuariosJpaController usuarioFacade;

    /**
     * Creates a new instance of CambiarClaveFormBean
     */
    public CambiarClaveFormBean() {
    }

    @PostConstruct
    public void iniciar() {
        priclave = "";
        confClave = "";
        actualClave = "";
    }

    public void guardar() {
        Boolean actualizo = false;
        FacesContext fc = FacesContext.getCurrentInstance();
        String nombre = usuario.getPrimerNombre() + usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isBlank() ? "" : usuario.getSegundoNombre() + usuario.getPrimerApellido() + usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isBlank() ? "" : usuario.getSegundoApellido();

        if (actualClave == null || actualClave.isEmpty()) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe digitar la contraseña actual."));
            return;
        }

        if (priclave == null || priclave.isEmpty()) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe digitar la nueva contraseña."));
            return;
        }

        if (confClave == null || confClave.isEmpty()) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe digitar la nueva contraseña."));
            return;
        }

        if (!confClave.equals(priclave)) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Los campos nueva contraseña y confirmar nueva contraseña no coinciden."));
            return;
        }

        UsuarioSession usuarioSession = (UsuarioSession) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("usuario");
        usuario = usuarioSession.getUsuarioLogeado();

        IndexBean indexBean = new IndexBean();

        if (usuario != null) {
            String encriptadoPri = indexBean.encriptar(priclave);
            String desencriptado = "";
            try {
              //  desencriptado = indexBean.Desencriptar(usuario.getContrasena());
            } catch (Exception ex) {
                Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (desencriptado.equals(actualClave)) {
                usuario.setContrasena(encriptadoPri);
                actualizo = usuarioFacade.actualizar(usuario);
            }

            if (actualizo) {
                UsuarioSession usuariosession = new UsuarioSession(nombre, usuario.getIdRol().getId(), usuario);
                fc.getExternalContext().getSessionMap().put("usuario", usuariosession);                
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La contraseña se actualizo correctamente"));
                return;
            }
        }

    }

    public String getPriclave() {
        return priclave;
    }

    public void setPriclave(String priclave) {
        this.priclave = priclave;
    }

    public String getConfClave() {
        return confClave;
    }

    public void setConfClave(String confClave) {
        this.confClave = confClave;
    }

    public String getActualClave() {
        return actualClave;
    }

    public void setActualClave(String actualClave) {
        this.actualClave = actualClave;
    }
}

