package entidades;

public class Entregador extends Usuario {

    private String placaMoto;
    private boolean disponivel;

    public Entregador() {
        super();
    }

    public Entregador(String nome, String cpf, String telefone, String placaMoto) {
        super(nome, cpf, telefone);
        this.placaMoto = placaMoto;
        this.disponivel = true;
    }

    public String getTipo() {
        return "Entregador";
    }

    public String getPlacaMoto() {
        return placaMoto;
    }

    public void setPlacaMoto(String placaMoto) {
        this.placaMoto = placaMoto;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
