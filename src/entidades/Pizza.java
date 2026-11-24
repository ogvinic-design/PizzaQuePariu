package entidades;

public class Pizza {

    private int codigo;
    private String sabor;
    private String tamanho;
    private double preco;

    public Pizza() {}

    public Pizza(int codigo, String sabor, String tamanho, double preco) {
        this.codigo = codigo;
        this.sabor = sabor;
        this.tamanho = tamanho;
        this.preco = preco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format("[%d] %-22s %-8s R$ %.2f", codigo, sabor, tamanho, preco);
    }
}
