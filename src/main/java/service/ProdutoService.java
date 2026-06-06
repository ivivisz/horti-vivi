package service;

import jakarta.persistence.EntityManager;
import model.Produto;
import util.JPAUtil;

import java.util.List;

public class ProdutoService {

    public void salvar(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Produto> listar() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM Produto", Produto.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(produto);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void excluir(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            Produto produto = em.find(Produto.class, id);

            if (produto != null) {
                em.getTransaction().begin();
                em.remove(produto);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}