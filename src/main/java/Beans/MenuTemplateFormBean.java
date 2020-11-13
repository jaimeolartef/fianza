/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import com.mycompany.fianza.entidades.Menus;
import com.mycompany.fianza.entidades.UsuarioSession;
import com.mycompany.fianza.facade.MenusJpaController;
import java.io.IOException;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author dianaplata
 */
public class MenuTemplateFormBean implements Serializable {

    @EJB
    private MenusJpaController menuFacade;
    private List<Menus> menus;
    private MenuModel model;
    private String nombreUsuario;
    private String fechaActual;
    private List<String> images;
    private String menuString;
    private static final String MENU_CONFIGURACION = "01";
    private static final String MENU_ADMISIONES = "02";
    private static final String MENU_ACADEMICO = "03";
    private static final String MENU_FINANCIERO = "04";
    private UsuarioSession usuarioSession;
    private boolean indVerConfiguracion = false;
    private boolean indVerAdmisiones = false;
    private boolean indVerAcademico = false;
    private boolean indVerFinanciero = false;

    @PostConstruct
    public void inicio() {

        images = new ArrayList<String>();
        for (int i = 1; i <= 4; i++) {
            images.add("nature" + i + ".jpg");
        }

        usuarioSession = (UsuarioSession) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("usuario");


        menus = menuFacade.getMenu(usuarioSession.getRol());

        for (Menus menu : menus) {
            if (menu.getNivel().length() == 2 && menu.getNivel().equals(MENU_ADMISIONES)) {
                indVerAdmisiones = true;
            } else if (menu.getNivel().length() == 2 && menu.getNivel().equals(MENU_CONFIGURACION)) {
                indVerConfiguracion = true;
            } else if (menu.getNivel().length() == 2 && menu.getNivel().equals(MENU_ACADEMICO)) {
                indVerAcademico = true;
            } else if (menu.getNivel().length() == 2 && menu.getNivel().equals(MENU_FINANCIERO)) {
                indVerFinanciero = true;
            }
        }

        if (usuarioSession != null) {
            nombreUsuario = usuarioSession.getUsuario();
        }

        if (usuarioSession.getModulo() != null) {
            if (usuarioSession.getModulo().equals(MENU_ACADEMICO)) {
                crearMenuAcademico();
            } else if (usuarioSession.getModulo().equals(MENU_ADMISIONES)) {
                crearMenuAdmisiones();
            } else if (usuarioSession.getModulo().equals(MENU_CONFIGURACION)) {
                crearMenuConfig();
            } else if (usuarioSession.getModulo().equals(MENU_FINANCIERO)) {
                crearMenuFinanciero();
            }
        }


        Timestamp fechaHora = new Timestamp(new Date().getTime());
        fechaActual = fechaHora.toString();
        fechaActual = convertirFechaStringStringLargo(fechaActual.substring(0, 10));

    }

    public void crearMenuConfig() {
        
        try {
            if (usuarioSession.getModulo() != null && !MENU_CONFIGURACION.equals(usuarioSession.getModulo())) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../MenuPrincipal/inicio.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuTemplateFormBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuarioSession.setModulo(MENU_CONFIGURACION);
        model = new DefaultMenuModel();
        DefaultSubMenu menuPadre;
        DefaultSubMenu menuConHijos = null;
        List<Menu> listaMenuPadres = new ArrayList<Menu>();

        for (Menu menu : menus) {
            if (menu.getNivel().length() == 2 && menu.getNivel().equals(MENU_CONFIGURACION)) {
                listaMenuPadres.add(menu);
            }
        }

        for (Menu menuPadreTmp : listaMenuPadres) {
            menuPadre = new DefaultSubMenu(menuPadreTmp.getNombre());
            for (Menu menu : menus) {
                if (menu.getNivel().substring(0, 2).equals(menuPadreTmp.getNivel().substring(0, 2))) {
                    if (menu.getNivel().length() == 5) {
                        boolean conHijos = false;
                        for (Menu menuHijos : menus) {
                            if (menuHijos.getNivel().length() >= 5) {
                                if (menuHijos.getNivel().substring(0, 5).equals(menu.getNivel().substring(0, 5))) {
                                    if (menuHijos.getNivel().length() > 5) {
                                        if (menuConHijos == null) {
                                            menuConHijos = new DefaultSubMenu(menu.getNombre());
                                        }
                                        DefaultMenuItem item = new DefaultMenuItem(menuHijos.getNombre());
                                        item.setUrl(menuHijos.getUrl());
                                        menuConHijos.addElement(item);
                                        conHijos = true;
                                    }
                                }
                            }
                        }

                        if (!conHijos) {
                            DefaultMenuItem item = new DefaultMenuItem(menu.getNombre());
                            item.setUrl(menu.getUrl());
                            menuPadre.addElement(item);
                        } else {
                            menuPadre.addElement(menuConHijos);
                        }
                    }
                }
            }
            model.addElement(menuPadre);
        }
    }

    public void crearMenuAdmisiones() {
        
        try {
            if (usuarioSession.getModulo() != null && !MENU_ADMISIONES.equals(usuarioSession.getModulo())) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../MenuPrincipal/inicio.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuTemplateFormBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuarioSession.setModulo(MENU_ADMISIONES);
        model = new DefaultMenuModel();
        DefaultSubMenu menuPadre;
        DefaultSubMenu menuConHijos = null;
        List<Menu> listaMenuPadres = new ArrayList<Menu>();

        for (Menu menu : menus) {
            if (menu.getNivel().length() == 2 && menu.getNivel().equals(MENU_ADMISIONES)) {
                listaMenuPadres.add(menu);
            }
        }

        for (Menu menuPadreTmp : listaMenuPadres) {
            menuPadre = new DefaultSubMenu(menuPadreTmp.getNombre());
            for (Menu menu : menus) {
                if (menu.getNivel().substring(0, 2).equals(menuPadreTmp.getNivel().substring(0, 2))) {
                    if (menu.getNivel().length() == 5) {
                        boolean conHijos = false;
                        for (Menu menuHijos : menus) {
                            if (menuHijos.getNivel().length() >= 5) {
                                if (menuHijos.getNivel().substring(0, 5).equals(menu.getNivel().substring(0, 5))) {
                                    if (menuHijos.getNivel().length() > 5) {
                                        if (menuConHijos == null) {
                                            menuConHijos = new DefaultSubMenu(menu.getNombre());
                                        }
                                        DefaultMenuItem item = new DefaultMenuItem(menuHijos.getNombre());
                                        item.setUrl(menuHijos.getUrl());
                                        menuConHijos.addElement(item);
                                        conHijos = true;
                                    }
                                }
                            }
                        }

                        if (!conHijos) {
                            DefaultMenuItem item = new DefaultMenuItem(menu.getNombre());
                            item.setUrl(menu.getUrl());
                            menuPadre.addElement(item);
                        } else {
                            menuPadre.addElement(menuConHijos);
                        }
                    }
                }
            }
            model.addElement(menuPadre);
        }
    }

    public void crearMenuAcademico() {
        
        try {
            if (usuarioSession.getModulo() != null && !MENU_ACADEMICO.equals(usuarioSession.getModulo())) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../MenuPrincipal/inicio.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuTemplateFormBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuarioSession.setModulo(MENU_ACADEMICO);
        model = new DefaultMenuModel();
        DefaultSubMenu menuPadre;
        DefaultSubMenu menuConHijos = null;
        List<Menu> listaMenuPadres = new ArrayList<Menu>();

        for (Menu menu : menus) {
            if (menu.getNivel().length() == 2 && menu.getNivel().equals(MENU_ACADEMICO)) {
                listaMenuPadres.add(menu);
            }
        }

        for (Menu menuPadreTmp : listaMenuPadres) {
            menuPadre = new DefaultSubMenu(menuPadreTmp.getNombre());
            for (Menu menu : menus) {
                if (menu.getNivel().substring(0, 2).equals(menuPadreTmp.getNivel().substring(0, 2))) {
                    if (menu.getNivel().length() == 5) {
                        boolean conHijos = false;
                        for (Menu menuHijos : menus) {
                            if (menuHijos.getNivel().length() >= 5) {
                                if (menuHijos.getNivel().substring(0, 5).equals(menu.getNivel().substring(0, 5))) {
                                    if (menuHijos.getNivel().length() > 5) {
                                        if (menuConHijos == null) {
                                            menuConHijos = new DefaultSubMenu(menu.getNombre());
                                        }
                                        DefaultMenuItem item = new DefaultMenuItem(menuHijos.getNombre());
                                        item.setUrl(menuHijos.getUrl());
                                        menuConHijos.addElement(item);
                                        conHijos = true;
                                    }
                                }
                            }
                        }

                        if (!conHijos) {
                            DefaultMenuItem item = new DefaultMenuItem(menu.getNombre());
                            item.setUrl(menu.getUrl());
                            menuPadre.addElement(item);
                        } else {
                            menuPadre.addElement(menuConHijos);
                        }
                    }
                }
            }
            model.addElement(menuPadre);
        }
    }

    public void crearMenuFinanciero() {
        try {
            if (usuarioSession.getModulo() != null && !MENU_FINANCIERO.equals(usuarioSession.getModulo())) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../MenuPrincipal/inicio.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuTemplateFormBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuarioSession.setModulo(MENU_FINANCIERO);
        model = new DefaultMenuModel();
        DefaultSubMenu menuPadre;
        DefaultSubMenu menuConHijos = null;
        List<Menu> listaMenuPadres = new ArrayList<Menu>();

        for (Menu menu : menus) {
            if (menu.getNivel().length() == 2 && menu.getNivel().equals(MENU_FINANCIERO)) {
                listaMenuPadres.add(menu);
            }
        }

        for (Menu menuPadreTmp : listaMenuPadres) {
            menuPadre = new DefaultSubMenu(menuPadreTmp.getNombre());
            for (Menu menu : menus) {
                if (menu.getNivel().substring(0, 2).equals(menuPadreTmp.getNivel().substring(0, 2))) {
                    if (menu.getNivel().length() == 5) {
                        boolean conHijos = false;
                        for (Menu menuHijos : menus) {
                            if (menuHijos.getNivel().length() >= 5) {
                                if (menuHijos.getNivel().substring(0, 5).equals(menu.getNivel().substring(0, 5))) {
                                    if (menuHijos.getNivel().length() > 5) {
                                        if (menuConHijos == null) {
                                            menuConHijos = new DefaultSubMenu(menu.getNombre());
                                        }
                                        DefaultMenuItem item = new DefaultMenuItem(menuHijos.getNombre());
                                        item.setUrl(menuHijos.getUrl());
                                        menuConHijos.addElement(item);
                                        conHijos = true;
                                    }
                                }
                            }
                        }

                        if (!conHijos) {
                            DefaultMenuItem item = new DefaultMenuItem(menu.getNombre());
                            item.setUrl(menu.getUrl());
                            menuPadre.addElement(item);
                        } else {
                            menuPadre.addElement(menuConHijos);
                        }
                    }
                }
            }
            model.addElement(menuPadre);
        }
    }

    public void cerrarSession() {
        try {
            FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().remove("usuario");
            FacesContext.getCurrentInstance().getExternalContext().redirect("../../");
        } catch (IOException ex) {
            Logger.getLogger(TemplateFormBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String convertirFechaStringStringLargo(String fechaTmp) {
        String fechaLargo = "";

        fechaLargo = fechaTmp.substring(8, 10).concat(" de "); //dia


        if (fechaTmp.substring(5, 7).equals("01")) {
            fechaLargo = fechaLargo.concat(" Enero del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("02")) {
            fechaLargo = fechaLargo.concat(" Febrero del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("03")) {
            fechaLargo = fechaLargo.concat(" Marzo del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("04")) {
            fechaLargo = fechaLargo.concat(" Abril del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("05")) {
            fechaLargo = fechaLargo.concat(" Mayo del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("06")) {
            fechaLargo = fechaLargo.concat(" Junio del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("07")) {
            fechaLargo = fechaLargo.concat(" Julio del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("08")) {
            fechaLargo = fechaLargo.concat(" Agosto del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("09")) {
            fechaLargo = fechaLargo.concat(" Septiembre del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("10")) {
            fechaLargo = fechaLargo.concat(" Octrubre del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("11")) {
            fechaLargo = fechaLargo.concat(" Noviembre del ").concat(fechaTmp.substring(0, 4));
        } else if (fechaTmp.substring(5, 7).equals("12")) {
            fechaLargo = fechaLargo.concat(" Diciembre del ").concat(fechaTmp.substring(0, 4));
        }

        return fechaLargo;
    }

    public static String convertirFechaString(Timestamp fecha) {
        String fechaString = "";

        String anio = fecha.toString().substring(0, 4);
        String mes = fecha.toString().substring(5, 7);
        String dia = fecha.toString().substring(8, 10);

        fechaString = dia + "/" + mes + "/" + anio;

        return fechaString;
    }

    public static Date convertirFechaDate(String fecha) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = fecha;
        Date date = new Date();

        try {

            date = formatter.parse(dateInString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertirFechaDateString(Date fecha) {
        Timestamp fechaTime = new Timestamp(fecha.getTime());
        String fechaString = convertirFechaString(fechaTime);
        return fechaString;
    }

    public static Double stringDouble(String valorString) {
        Double valor = 0.0;

        valor = Double.parseDouble(valorString);

        return valor;
    }

    public void irainicio() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("../MenuPrincipal/inicio.xhtml");
    }

    public List<Menu> getMenu() {
        return menus;
    }

    public void setMenu(List<Menu> menus) {
        this.menus = menus;
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getMenuString() {
        return menuString;
    }

    public void setMenuString(String menuString) {
        this.menuString = menuString;
    }

    public boolean isIndVerConfiguracion() {
        return indVerConfiguracion;
    }

    public void setIndVerConfiguracion(boolean indVerConfiguracion) {
        this.indVerConfiguracion = indVerConfiguracion;
    }

    public boolean isIndVerAdmisiones() {
        return indVerAdmisiones;
    }

    public void setIndVerAdmisiones(boolean indVerAdmisiones) {
        this.indVerAdmisiones = indVerAdmisiones;
    }

    public boolean isIndVerAcademico() {
        return indVerAcademico;
    }

    public void setIndVerAcademico(boolean indVerAcademico) {
        this.indVerAcademico = indVerAcademico;
    }

    public boolean isIndVerFinanciero() {
        return indVerFinanciero;
    }

    public void setIndVerFinanciero(boolean indVerFinanciero) {
        this.indVerFinanciero = indVerFinanciero;
    }
}

