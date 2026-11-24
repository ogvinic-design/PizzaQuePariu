import java.util.Scanner;
import util.Funcionalidades;

public class PizzaQuePariu {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Funcionalidades sistema = new Funcionalidades(sc);

        int opcao = 0;

        do {
            System.out.println("\n=== PIZZA QUE PARIU - DELIVERY ===");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Cadastrar Entregador");
            System.out.println("3 - Fazer pedido");
            System.out.println("4 - Mudar status do pedido");
            System.out.println("5 - Ver cardapio");
            System.out.println("6 - Sair");
            System.out.print("Opção: ");

            try {
                String entrada = sc.nextLine().trim();
                opcao = Integer.parseInt(entrada);

                switch (opcao) {
                    case 1: sistema.telaCadastrarCliente();
                    break;

                    case 2: sistema.telaCadastrarEntregador();
                    break;

                    case 3: sistema.telaFazerPedido();
                    break;

                    case 4: sistema.telaMudarStatus();
                    break;

                    case 5: sistema.listarCardapio();
                    break;

                    case 6: System.out.println("Fechando a pizzaria... até a próxima fornada!");
                    break;

                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException erro) {
                System.out.println("Erro: Digite apenas o NÚMERO da opção.");
            }

        } while (opcao != 6);

        sc.close();
    }
}
