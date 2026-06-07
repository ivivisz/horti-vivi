package bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import model.Usuario;
import service.UsuarioService;

import java.io.Serializable;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {

    private Usuario usuario = new Usuario();
    private UsuarioService service = new UsuarioService();

    public String cadastrar() {

        if (service.buscarPorEmail(usuario.getEmail()) != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Este e-mail já está cadastrado.", null));
            return null;
        }

        service.cadastrar(usuario);
        usuario = new Usuario();

        return "login.xhtml?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}