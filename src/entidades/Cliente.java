package entidades;

import java.util.List;

public class Cliente extends Usuario {

    private String endereco;
    private List<Pedido> pedidos;

    public Cliente() {
        super();
    }

    public Cliente(String nome, String cpf, String telefone, String endereco, List<Pedido> pedidos) {
        super(nome, cpf, telefone);
        this.endereco = endereco;
        this.pedidos = pedidos;
    }

    public String getTipo() {
        return "Cliente";
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
