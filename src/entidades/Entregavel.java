package entidades;

public interface Entregavel {
    String getDescricao();
    double calcularPrecoFinal();
    void avancarStatus();
}
