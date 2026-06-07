package bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import model.Produto;
import service.ProdutoService;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProdutoBean implements Serializable {

    private Produto produto = new Produto();

    private ProdutoService service = new ProdutoService();

    private String termoPesquisa;

    public String salvar() {
        service.salvar(produto);
        produto = new Produto();
        return "lista.xhtml?faces-redirect=true";
    }

    public List<Produto> getProdutos() {
        return service.listar();
    }

    public String prepararEdicao(Produto produtoSelecionado) {
        this.produto = produtoSelecionado;
        return "index.xhtml?faces-redirect=true";
    }

    public String atualizar() {
        service.atualizar(produto);
        produto = new Produto();
        return "lista.xhtml?faces-redirect=true";
    }

    public String excluir(Long id) {
        service.excluir(id);
        return "lista.xhtml?faces-redirect=true";
    }

    public String novo() {
        produto = new Produto();
        return "index.xhtml?faces-redirect=true";
    }

    public boolean isEditando() {
        return produto != null && produto.getId() != null;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public String getValorTotalFormatado() {
        double total = 0;

        for (Produto p : getProdutos()) {
            total += p.getPreco() * p.getQuantidade();
        }

        java.text.NumberFormat formato =
                java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));

        return formato.format(total);
    }

    public long getProdutosEmBaixa() {
        return getProdutos()
                .stream()
                .filter(p -> p.getQuantidade() < 5)
                .count();
    }

    public int getQuantidadeTotalEstoque() {
        int total = 0;

        for (Produto p : getProdutos()) {
            total += p.getQuantidade();
        }

        return total;
    }

    public List<Produto> getProdutosFiltrados() {
        if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
            return getProdutos();
        }

        return getProdutos()
                .stream()
                .filter(p -> p.getNome().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .toList();
    }

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

    public Produto getProdutoMaiorEstoque() {
        return getProdutos().stream()
                .max((p1, p2) -> Integer.compare(p1.getQuantidade(), p2.getQuantidade()))
                .orElse(null);
    }

    public Produto getProdutoMenorEstoque() {
        return getProdutos().stream()
                .min((p1, p2) -> Integer.compare(p1.getQuantidade(), p2.getQuantidade()))
                .orElse(null);
    }

    public Produto getProdutoMaisCaro() {
        return getProdutos().stream()
                .max((p1, p2) -> Double.compare(p1.getPreco(), p2.getPreco()))
                .orElse(null);
    }

    public Produto getProdutoMaisBarato() {
        return getProdutos().stream()
                .min((p1, p2) -> Double.compare(p1.getPreco(), p2.getPreco()))
                .orElse(null);
    }

    public int getMaiorQuantidade() {
        return getProdutos().stream()
                .mapToInt(Produto::getQuantidade)
                .max()
                .orElse(1);
    }

    public String getClasseEstoque(Produto produto) {
        if (produto.getQuantidade() < 3) {
            return "barra-baixa";
        } else if (produto.getQuantidade() <= 5) {
            return "barra-media";
        } else {
            return "barra-alta";
        }
    }
}