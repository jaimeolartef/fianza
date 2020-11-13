/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import com.mycompany.fianza.entidades.UsuarioSession;
import com.mycompany.fianza.entidades.Usuarios;
import com.mycompany.fianza.facade.ConstantesJpaController;
import com.mycompany.fianza.facade.UsuariosJpaController;
import java.io.IOException;
import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.datatype.DatatypeFactory;

/**
 *
 * @author dianaplata
 */
public class IndexBean implements Serializable {

    

    private String nombre;
    private String contraseña;
    private Usuarios usuario;
    @EJB
    private UsuariosJpaController usuarioFacade;
     @EJB
     private ConstantesJpaController constanteFacade;
    
    private String correo;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private final Properties properties = new Properties();
    private Session session;
    
    

    @PostConstruct
    public void iniciar() {
        correo = "";
        nombre= usuario.getPrimerNombre() + usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isBlank() ? "" : usuario.getSegundoNombre() + usuario.getPrimerApellido() + usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isBlank() ? "" : usuario.getSegundoApellido();
    }

    public void ingresar() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (nombre == null || nombre.isEmpty()) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe ingresar el usuario"));
            return;
        }

        if (contraseña == null || contraseña.isEmpty()) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe ingresar la contraseña"));
            return;
        }

        usuario = usuarioFacade.getUsuario(nombre);


        if (usuario != null) {

            String encriptado = encriptar(contraseña);
            

            if (usuario.getContrasena().equals(encriptado)) {
                UsuarioSession usuariosession = new UsuarioSession(nombre, usuario.getIdRol().getId(), usuario);
                fc.getExternalContext().getSessionMap().put("usuario", usuariosession);
                fc.getExternalContext().redirect("faces/MenuPrincipal/inicio.xhtml");//redirecciona la página
            } else {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El usuario y la contraseña no coinciden"));
            }
        } else {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El usuario no esta registrado en el sistema."));

        }

    }

    public void recupeClave() {

        try {
            pattern = Pattern.compile(EMAIL_PATTERN);
            FacesContext fc = FacesContext.getCurrentInstance();

            if (!pattern.matcher(correo.toString()).matches()) {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "Debe digitar un correo electronico valido"));
                return;
            }

            Usuarios usuarioRecup = usuarioFacade.getUsuarioByCorreo(correo);

            if (usuarioRecup == null) {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "El correo electronico no esta asociado a ningún usuario"));
                return;
            }
                      
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            
            final String usuarioCorreo = constanteFacade.constanteByCodConstante("CorRec").getValor();
//            final String claveCorreo = Desencriptar(constanteFacade.constanteByCodConstante("ClaCor").getValor());
            
//            session = Session.getInstance(properties,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(usuarioCorreo, claveCorreo);
//                        }
//                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(usuarioCorreo));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(correo));
                message.setSubject("Recuperación de contraseña");
                message.setText("Su usuario es " + usuarioRecup.getCodigo()+ " y su contraseña es " + "\n\n\n" + "Administrador UniRemington");
                message.setSentDate(new Date());

                Transport.send(message);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Su usuario y contraseña fueron enviados correctamente."));
            } catch (MessagingException e) {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Error al intentar enviar el correo"));
                throw new RuntimeException(e);
            }   

        } catch (Exception ex) {
            Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
        }   

    }
    
    public String encriptar(String clave) {
        String encriptado = "";
        try {
            byte[] salt = getSalt();
            encriptado = get_SHA_512_SecurePassword(clave, salt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return encriptado;
    }
    
    private byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
    private String get_SHA_512_SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
}

