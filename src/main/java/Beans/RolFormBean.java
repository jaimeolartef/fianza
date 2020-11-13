/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import com.mycompany.fianza.entidades.Roles;
import com.mycompany.fianza.facade.RolesJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author dianaplata
 */
public class RolFormBean implements Serializable {

    private String nombreRol;
    //private List<Menu> menus;
    @EJB
    //private MenuFacade menuFacade;
    private TreeNode menusArbol;
    private TreeNode[] nodoSeleccionado;
    @EJB
    private RolesJpaController rolFacade;
    @EJB
    //private RolMenuFacade rolMenuFacade;
    private List<Roles> roles;
    private Roles rolSeleccionado;
    private boolean actualizar;
    //List<Menu> menusTmp;
    private static final int NIVEL_PADRE = 2;

    public RolFormBean() {
    }

    @PostConstruct
    public void iniciar() {
        nombreRol = "";
        roles = new ArrayList<Roles>();
        rolSeleccionado = new Roles();
        actualizar = false;
      //  menusTmp = new ArrayList<Menu>();

        cargarMenu();
    }
    
    public void cargarMenu() {
       // menusArbol = new CheckboxTreeNode(new MenuDTO(0, "Menu"));
        //menus = menuFacade.findAll();

       // for (Menu menu : menus) {
            //if (menu.getNivel().length() == 2) {
             //   TreeNode padre = new CheckboxTreeNode(new MenuDTO(menu.getIdMenu(), menu.getNombre()), menusArbol);

              //  for (Menu menuHijo : menus) {
                //    String nivel = menuHijo.getNivel().substring(0, 2);
                //    if (nivel.equals(menu.getNivel()) && menuHijo.getIdMenu() != menu.getIdMenu()) {
                 //       TreeNode hijo = new CheckboxTreeNode(new MenuDTO(menuHijo.getIdMenu(), menuHijo.getNombre()), padre);
             //       }
              //  }
           // }
       // }
    }
    
    public void limpiar() {
        nombreRol = "";
        actualizar = false;
        //menusTmp = new ArrayList<Menu>();
        cargarMenu();
    }

    public void guardar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Roles rol = new Roles();
        
//        List<Roles> roles = rolFacade.buscar(nombreRol);
        
        if (!actualizar) {
            for (Roles item : roles) {
                if (item.getNombre().equalsIgnoreCase(nombreRol)) {
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ya existe un rol con el mismo nombre."));
                    return;
                }
            }
            
            rol.setNombre(nombreRol);

//            Boolean guardar = rolFacade.guardarRol(rol);

//            if (guardar) {
//               // List<RolMenu> rolMenus = new ArrayList<RolMenu>();
//                for (TreeNode node : nodoSeleccionado) {
//                    //RolMenu rolMenu = new RolMenu();
//                   // RolMenu rolMenuPadre = new RolMenu();
//
//                    if (node.getParent().getData().hashCode() != 0) {
//                        //rolMenu.setIdMenu(new Menu());
//                        //rolMenu.getIdMenu().setIdMenu(node.getData().hashCode());
//                        //rolMenu.setIdRol(new Roles());
//                        //rolMenu.getIdRol().setIdRol(rol.getId());
//                        //rolMenus.add(rolMenu);
//                    }
//
//                    if (node.getParent().getData().hashCode() == 0) {
//                       // rolMenuPadre.setIdMenu(new Menu());
//                        //rolMenuPadre.getIdMenu().setIdMenu(node.getData().hashCode());
//                       // rolMenuPadre.setIdRol(new Roles());
//                       // rolMenuPadre.getIdRol().setIdRol(rol.getId());
//                    } else {
//                        //rolMenuPadre.setIdMenu(new Menu());
//                        //rolMenuPadre.getIdMenu().setIdMenu(node.getParent().getData().hashCode());
//                        //rolMenuPadre.setIdRol(new Roles());
//                       // rolMenuPadre.getIdRol().setIdRol(rol.getId());
//                    }
//
//                    Boolean agregarPadre = true;
////               rolMenu.getIdMenu().setIdMenu(node.getParent().getData().hashCode());
//                    //for (RolMenu rm : rolMenus) {
//                     //   if (rm.getIdMenu().getIdMenu().intValue() == rolMenuPadre.getIdMenu().getIdMenu().intValue()) {
//                      //      agregarPadre = false;
//                      //      break;
//                      //  }
//                   // }
//
//                    if (agregarPadre) {
//                      //  rolMenus.add(rolMenuPadre);
//                    }
//                }
//
//               // guardar = rolMenuFacade.guardar(rolMenus);
//            }

//           if (guardar) {
//                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El registro se guardo correctamente."));
//                limpiar();
//            }
//        } else {
           // List<RolMenu> rolMenus = rolMenuFacade.getRolMenuByIdRol(rolSeleccionado.getIdRol());
           // List<RolMenu> rolMenusAgregar = new ArrayList<RolMenu>();
           // List<RolMenu> rolMenusBorrar = new ArrayList<RolMenu>();
            rol = rolSeleccionado;
            boolean agregar = true;
            boolean actualizar = false;
            boolean borrar = true;

            for (TreeNode node : nodoSeleccionado) {

                //for (RolMenu rolMenu : rolMenus) {
//                    if (rolMenu.getIdMenu().getIdMenu().intValue() == node.getData().hashCode()) {
//                        agregar = false;
//                        break;
//                    }
                //}

                if (agregar) {
                   // RolMenu rolMenu = new RolMenu();
                   // RolMenu rolMenuPadre = new RolMenu();

                    if (node.getParent().getData().hashCode() != 0) {
//                        rolMenu.setIdMenu(new Menu());
//                        rolMenu.getIdMenu().setIdMenu(node.getData().hashCode());
//                        rolMenu.setIdRol(new Rol());
//                        rolMenu.getIdRol().setIdRol(rol.getId());
//                        rolMenusAgregar.add(rolMenu);
                    }

//                    if (node.getParent().getData().hashCode() == 0) {
//                        rolMenuPadre.setIdMenu(new Menu());
//                        rolMenuPadre.getIdMenu().setIdMenu(node.getData().hashCode());
//                        rolMenuPadre.setIdRol(new Roles());
//                        rolMenuPadre.getIdRol().setIdRol(rol.getId());
//                    } else {
//                        rolMenuPadre.setIdMenu(new Menu());
//                        rolMenuPadre.getIdMenu().setIdMenu(node.getParent().getData().hashCode());
//                        rolMenuPadre.setIdRol(new Roles());
//                        rolMenuPadre.getIdRol().setIdRol(rol.getId());
//                    }

                    Boolean agregarPadre = true;
//               rolMenu.getIdMenu().setIdMenu(node.getParent().getData().hashCode());
//                    for (RolMenu rm : rolMenusAgregar) {
//                        if (rm.getIdMenu().getIdMenu().intValue() == rolMenuPadre.getIdMenu().getIdMenu().intValue()) {
//                            agregarPadre = false;
//                            break;
//                        }
//                    }
//                    
//                    for (RolMenu rolMe : rolMenus) {
//                        if (rolMe.getIdMenu().getIdMenu().intValue() == rolMenuPadre.getIdMenu().getIdMenu().intValue()) {
//                            agregarPadre = false;
//                            break;
//                        }
//                    }
//
//                    if (agregarPadre) {
//                        rolMenusAgregar.add(rolMenuPadre);
//                    }
//                }
                agregar = true;
            }
            
//            for (RolMenu rolMenu : rolMenus) {
//                for (TreeNode node : nodoSeleccionado) {
//                    if (rolMenu.getIdMenu().getIdMenu().intValue() == node.getData().hashCode() 
//                            || rolMenu.getIdMenu().getNivel().length() == NIVEL_PADRE) {
//                        borrar = false;
//                    }
//                }
//                
//                if (borrar) {
//                    rolMenusBorrar.add(rolMenu);                    
//                }
                borrar = true;
            }

//            actualizar = rolMenuFacade.actualizarMenu(rolMenusBorrar, rolMenusAgregar);
//            
//            if (actualizar) {
//                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El registro se actualizo correctamente."));
//                limpiar();
//            }
        }
    }
    
    public void buscarRol(){
        rolSeleccionado =  new Roles();
//        roles = rolFacade.findAll();
//        RequestContext.getCurrentInstance().execute("PF('dlgRolWV').show();");
    }
    
    public void cargarRol(){
        nombreRol = rolSeleccionado.getNombre();
       // menusTmp = rolMenuFacade.getRolMenu(rolSeleccionado.getId());
      //  nodoSeleccionado = new CheckboxTreeNode[menusTmp.size()];
        int i = 0;
       // menusArbol = new CheckboxTreeNode(new MenuDTO(0, "Menu"));
      

//        for (Menu menu : menus) {
//            if (menu.getNivel().length() == 2) {
//                TreeNode padre = new CheckboxTreeNode(new MenuDTO(menu.getIdMenu(), menu.getNombre()), menusArbol);
//                
//                for (Menu menuTmp : menusTmp) {
//                    if (menuTmp.getIdMenu().intValue() == menu.getIdMenu().intValue()) {
//                        padre.setSelected(true);
//                        padre.setExpanded(true);
//                        break;
//                    }
//                }
//
//                for (Menu menuHijo : menus) {
//                    String nivel = menuHijo.getNivel().substring(0, 2);
//                    if (nivel.equals(menu.getNivel()) && menuHijo.getIdMenu() != menu.getIdMenu()) {
//                        TreeNode hijo = new CheckboxTreeNode(new MenuDTO(menuHijo.getIdMenu(), menuHijo.getNombre()), padre);
//                        for (Menu menuHijoTmp : menusTmp) {
//                            if (menuHijoTmp.getIdMenu().intValue() == menuHijo.getIdMenu().intValue()) {
//                                hijo.setSelected(true);
//                                break;
//                            }
//                        }
//                    }
//                }
           // }
       // }
        
        actualizar = true;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public TreeNode getMenusArbol() {
        return menusArbol;
    }

    public void setMenusArbol(TreeNode menusArbol) {
        this.menusArbol = menusArbol;
    }

    public TreeNode[] getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    public void setNodoSeleccionado(TreeNode[] nodoSeleccionado) {
        this.nodoSeleccionado = nodoSeleccionado;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public Roles getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Roles rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }
    
    
}
