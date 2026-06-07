package bean;

import model.Usuario;
import service.UsuarioService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String usuario;
    private String senha;
    private boolean logado = false;

    private UsuarioService usuarioService = new UsuarioService();

    public String entrar() {

        Usuario usuarioEncontrado =
                usuarioService.buscarPorEmail(usuario);

        if (usuarioEncontrado != null &&
                usuarioEncontrado.getSenha().equals(senha)) {

            logado = true;
            return "index.xhtml?faces-redirect=true";
        }

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Usuário ou senha inválidos",
                        null
                )
        );

        return null;
    }

    public String sair() {
        logado = false;
        usuario = null;
        senha = null;
        return "login.xhtml?faces-redirect=true";
    }

    public boolean isLogado() {
        return logado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String verificarLogin() {

        if (!logado) {
            return "login.xhtml?faces-redirect=true";
        }

        return null;
    }

}