package entidades;

public class PedidoRetirada extends Pedido {

    private static final String[] ETAPAS = {"RECEBIDO", "NO FORNO", "PRONTO NO BALCAO", "RETIRADO"};

    private String observacao;

    public PedidoRetirada() {
        super();
    }

    public PedidoRetirada(int numero, Cliente cliente, Pizza pizza, int quantidade, String observacao) {
        super(numero, cliente, pizza, quantidade);
        this.observacao = observacao;
    }

    protected String[] getEtapas() {
        return ETAPAS;
    }

    // Quem busca no balcão ganha 10% de desconto
    public double calcularPrecoFinal() {
        return getSubtotal() * 0.90;
    }

    public String getDescricao() {
        return quantidade + "x " + pizza.getSabor() + " (" + pizza.getTamanho() + ") - Retirada por "
                + cliente.getNome() + " - Obs: " + observacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
