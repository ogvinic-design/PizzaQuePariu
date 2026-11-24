package entidades;

import java.time.LocalDateTime;

public abstract class Pedido implements Entregavel {

    protected int numero;
    protected LocalDateTime dataHora;
    protected Cliente cliente;
    protected Pizza pizza;
    protected int quantidade;
    protected int etapaAtual;

    public Pedido() {}

    public Pedido(int numero, Cliente cliente, Pizza pizza, int quantidade) {
        this.numero = numero;
        this.dataHora = LocalDateTime.now();
        this.cliente = cliente;
        this.pizza = pizza;
        this.quantidade = quantidade;
        this.etapaAtual = 0;
    }

    // Cada tipo de pedido tem o seu próprio caminho de status
    protected abstract String[] getEtapas();

    public void avancarStatus() {
        if (isFinalizado()) {
            throw new IllegalStateException("O pedido #" + numero + " já foi finalizado.");
        }
        etapaAtual++;
    }

    public boolean isFinalizado() {
        return etapaAtual == getEtapas().length - 1;
    }

    public String getStatus() {
        return getEtapas()[etapaAtual];
    }

    protected double getSubtotal() {
        return pizza.getPreco() * quantidade;
    }

    public int getNumero() {
        return numero;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
