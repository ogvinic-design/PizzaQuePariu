package entidades;

public class PedidoDelivery extends Pedido {

    private static final String[] ETAPAS = {"RECEBIDO", "NO FORNO", "SAIU PARA ENTREGA", "ENTREGUE"};

    private Entregador entregador;
    private double taxaEntrega;

    public PedidoDelivery() {
        super();
    }

    public PedidoDelivery(int numero, Cliente cliente, Pizza pizza, int quantidade, Entregador entregador, double taxaEntrega) {
        super(numero, cliente, pizza, quantidade);
        this.entregador = entregador;
        this.taxaEntrega = taxaEntrega;
    }

    protected String[] getEtapas() {
        return ETAPAS;
    }

    @Override
    public void avancarStatus() {
        super.avancarStatus();
        // Quando entrega, o motoboy fica livre de novo
        if (isFinalizado()) {
            entregador.setDisponivel(true);
        }
    }

    public double calcularPrecoFinal() {
        return getSubtotal() + taxaEntrega;
    }

    public String getDescricao() {
        return quantidade + "x " + pizza.getSabor() + " (" + pizza.getTamanho() + ") para "
                + cliente.getNome() + " em " + cliente.getEndereco()
                + " - Motoboy: " + entregador.getNome();
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }
}
