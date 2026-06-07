package service;

import jakarta.persistence.EntityManager;
import model.Usuario;
import util.JPAUtil;

import java.util.List;

public class UsuarioService {

    public void cadastrar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            List<Usuario> usuarios = em.createQuery(
                            "SELECT u FROM Usuario u WHERE LOWER(u.email) = LOWER(:email)",
                            Usuario.class
                    ).setParameter("email", email.trim())
                    .getResultList();

            if (usuarios.isEmpty()) {
                return null;
            }

            return usuarios.get(0);

        } finally {
            em.close();
        }
    }

    public boolean validarLogin(String email, String senha) {
        Usuario usuario = buscarPorEmail(email);

        if (usuario == null) {
            return false;
        }

        return usuario.getSenha().equals(senha);
    }
}