/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;


import com.mycompany.fianza.entidades.Listas;
import com.mycompany.fianza.entidades.ListasGrupo;
import com.mycompany.fianza.entidades.Promotores;
import com.mycompany.fianza.entidades.Roles;
import com.mycompany.fianza.entidades.Usuarios;
import com.mycompany.fianza.facade.ListasJpaController;
import com.mycompany.fianza.facade.RolesJpaController;
import com.mycompany.fianza.facade.UsuariosJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.context.PrimeRequestContext;

/**
 *
 * @author dianaplata
 */
@ViewScoped
public class UsuarioFormBean implements Serializable {
    
    private Usuarios usuario;
    private String nombre;
    private Integer tipoDocumento;
    private Integer cargo;
    private String numDocumento;
    private Integer rol;
    private String codigo;
    private String direccion;
    private String telefono;
    private String Correo;
    private String priclave;
    private String confClave;
    private List<Listas> listaDocumentos = new ArrayList<Listas>();
    private List<Roles> roles = new ArrayList<Roles>();
    private List<Promotores> cargos = new ArrayList<Promotores>();
    private Boolean actualizar = false;
    private Boolean actualizarSinClave = false;
    private Boolean deshabilitarclave = false;
    private Pattern pattern;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @EJB
   private ListasJpaController  listaFacade;
    @EJB
    private RolesJpaController rolFacade;
    @EJB
    private UsuariosJpaController usuarioFacade;
   
    
    @PostConstruct
    public void inicio() {
        usuario = new Usuarios();
       // listaDocumentos = listaFacade;
        roles = rolFacade.findRolesEntities();
        
        limpiar();
    }
    
    
    public void limpiar() {
        tipoDocumento = 0;
        cargo = 0;
        numDocumento = "";
        nombre = "";
        rol = 0;
        codigo = "";
        direccion = "";
        telefono = "";
        Correo = "";
        priclave = "";
        confClave = "";
        actualizar = false;
        actualizarSinClave = false;
        deshabilitarclave = false;
       deshabilitarClave();
    }

    
    public void buscar() {
        usuario = usuarioFacade.getUsuarioByDocumento(numDocumento);
        if (usuario != null) {
            tipoDocumento = usuario.getTipodocumento().getId();
            numDocumento = usuario.getIdentificacion();
            nombre = usuario.getPrimerNombre() + usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isBlank() ? "" : usuario.getSegundoNombre() + usuario.getPrimerApellido() + usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isBlank() ? "" : usuario.getSegundoApellido();
            rol = usuario.getIdRol().getId();
            codigo = usuario.getCodigo();  
            Correo = usuario.getCorreo();
            
            actualizar = true;
        } else {
            usuario = new Usuarios();
            limpiar();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se encontro resultados"));
            actualizar = false;
        }
    }
    
    public void validar() {

        Boolean validar = false;
        FacesContext fc = FacesContext.getCurrentInstance();
        pattern = Pattern.compile(EMAIL_PATTERN);

        if (!pattern.matcher(Correo.toString()).matches()) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe digitar un correo electronico valido"));
            return;
        }

        if (tipoDocumento != 0) {
            if (!numDocumento.trim().isEmpty()) {
                if (!nombre.trim().isEmpty()) {
                    if (rol != 0) {
                        if (cargo != 0) {
                            if (!codigo.trim().isEmpty()) {
                                if (!actualizar) {
                                    if (usuarioFacade.getUsuario(codigo) == null && !actualizar) {
                                        if (usuarioFacade.getUsuarioByDocumento(numDocumento) == null && !actualizar) {
                                            validar = true;
                                        } else {
                                            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Este número de documento ya se encuentra asignado para otro usuario"));
                                            return;
                                        }
                                    } else {
                                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", " Este código ya se encuentra asignado para otro usuario"));
                                        return;
                                    }
                                } else {
                                    validar = true;
                                }
                            } else {
                                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe digitar el codigo del usuario"));
                                return;
                            }
                        } else {
                            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar el cargo del usuario"));
                            return;
                        }
                    } else {
                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar el rol del usuario"));
                        return;
                    }
                } else {
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe digitar el nombre del usuario"));
                    return;
                }
            } else {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe digitar el numero de documento"));
                return;
            }
        } else {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar el tipo de documento"));
            return;
        }

        if (validar) {
            PrimeRequestContext.getCurrentInstance().getScriptsToExecute().add("PF('dlgConfWV').show();");
            
        }
    }

    public void deshabilitarClave() {
        if (actualizarSinClave) {
            deshabilitarclave = true;
        } else {
            deshabilitarclave = false;
        }

    }

    public void email() {
        pattern = Pattern.compile(EMAIL_PATTERN);
        FacesContext fc = FacesContext.getCurrentInstance();


        if (!pattern.matcher(Correo.toString()).matches()) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe digitar un correo electronico valido"));
        }

        Usuarios usuarioRecup = usuarioFacade.getUsuarioByCorreo(Correo);

        if (usuarioRecup != null && !actualizar) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El correo electronico esta asociado a otro usuario"));
            return;
        }
    }
    
    
    public void guardar() {

        Boolean contraseña = false;
        FacesContext fc = FacesContext.getCurrentInstance();

        if (actualizar) {
            if (actualizarSinClave) {
                contraseña = true;
            } else {
                if (priclave.equals(confClave)) {
                    contraseña = true;
                }
            }
        } else {
            if (priclave.equals(confClave)) {
                contraseña = true;
            }
        }

        if (!contraseña) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Los campos contraseña y confirmar contraseña no coinciden"));
            return;
        }

        Boolean guardado = false;
        if (actualizar) {
            if (!actualizarSinClave) {
                IndexBean indexBean = new IndexBean();
                String claveEncrip = indexBean.encriptar(priclave);
                usuario.setContrasena(claveEncrip);
            }

            usuario.setCorreo(Correo);
            
            usuario.setTipodocumento(new ListasGrupo());
            usuario.getTipodocumento().setId(tipoDocumento);
            usuario.setIdRol(new Roles());
            usuario.getIdRol().setId(rol);
            usuario.setPrimerNombre(nombre);
            
            

            guardado = usuarioFacade.actualizar(usuario);
        } else {
            Usuarios usuariotmp = new Usuarios();

            IndexBean indexFormBean = new IndexBean();
            String claveEncrip = indexFormBean.encriptar(priclave);
            usuariotmp.setContrasena(claveEncrip);

            usuariotmp.setCodigo(codigo);
            usuariotmp.setCorreo(Correo);
            
            usuariotmp.setIdentificacion(numDocumento);
            usuariotmp.setTipodocumento(new ListasGrupo());
            usuariotmp.getTipodocumento().setId(tipoDocumento);
            usuariotmp.setIdRol(new Roles());
            usuariotmp.getIdRol().setId(rol);
            usuariotmp.setPrimerNombre(nombre);
            
            guardado = usuarioFacade.guardar(usuariotmp);
        }

        if (guardado) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario se guardo correctamente"));
            limpiar();
        }
    }


    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
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

    public List<Listas> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<Listas> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Promotores> getCargos() {
        return cargos;
    }

    public void setCargos(List<Promotores> cargos) {
        this.cargos = cargos;
    }

    public Boolean getActualizar() {
        return actualizar;
    }

    public void setActualizar(Boolean actualizar) {
        this.actualizar = actualizar;
    }

    public Boolean getActualizarSinClave() {
        return actualizarSinClave;
    }

    public void setActualizarSinClave(Boolean actualizarSinClave) {
        this.actualizarSinClave = actualizarSinClave;
    }

    public Boolean getDeshabilitarclave() {
        return deshabilitarclave;
    }

    public void setDeshabilitarclave(Boolean deshabilitarclave) {
        this.deshabilitarclave = deshabilitarclave;
    }

    public ListasJpaController getListaFacade() {
        return listaFacade;
    }

    public void setListaFacade(ListasJpaController listaFacade) {
        this.listaFacade = listaFacade;
    }

    public RolesJpaController getRolFacade() {
        return rolFacade;
    }

    public void setRolFacade(RolesJpaController rolFacade) {
        this.rolFacade = rolFacade;
    }

    public UsuariosJpaController getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuariosJpaController usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }
    
    
    
    
    
}
