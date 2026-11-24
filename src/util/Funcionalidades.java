package util;

import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entidades.*;

public class Funcionalidades {

    private static final double TAXA_ENTREGA = 7.00;
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("dd/MM HH:mm");

    private List<Usuario> usuariosCadastrados;
    private List<Pedido> pedidos;
    private List<Pizza> cardapio;
    private int proximoNumeroPedido;
    private Scanner sc;

    public Funcionalidades(Scanner sc) {
        this.usuariosCadastrados = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.cardapio = new ArrayList<>();
        this.proximoNumeroPedido = 1;
        this.sc = sc;
        montarCardapio();
    }

    private void montarCardapio() {
        cardapio.add(new Pizza(1, "Calabresa Acebolada", "Grande", 49.90));
        cardapio.add(new Pizza(2, "Frango com Catupiry", "Grande", 54.90));
        cardapio.add(new Pizza(3, "Quatro Queijos", "Grande", 57.90));
        cardapio.add(new Pizza(4, "Portuguesa", "Media", 42.90));
        cardapio.add(new Pizza(5, "Marguerita", "Media", 39.90));
        cardapio.add(new Pizza(6, "Chocolate com Morango", "Broto", 32.90));
    }

    public void telaCadastrarCliente() {
        System.out.println("CADASTRO DE CLIENTE");
        try {
            System.out.print("Nome Completo: ");
            String nome = sc.nextLine();

            String cpf = lerCpfNovo();
            String telefone = lerTelefone();

            System.out.print("Endereço de entrega (Rua, nº, bairro): ");
            String endereco = sc.nextLine();

            if (endereco.isBlank()) {
                throw new IllegalArgumentException("Endereço não pode ficar vazio.");
            }

            Cliente novoCliente = new Cliente(nome, cpf, telefone, endereco, new ArrayList<>());
            usuariosCadastrados.add(novoCliente);
            System.out.println("Cliente " + nome + " cadastrado! Bora pedir uma pizza?");

        } catch (IllegalArgumentException erro) {
            System.out.println("Erro: " + erro.getMessage());

        } catch (Exception erro) {
            System.out.println("Erro inesperado: " + erro.getMessage());
        }
    }

    public void telaCadastrarEntregador() {
        System.out.println("CADASTRO DE ENTREGADOR");
        try {
            System.out.print("Nome Completo: ");
            String nome = sc.nextLine();

            String cpf = lerCpfNovo();
            String telefone = lerTelefone();

            System.out.print("Placa da moto (Ex: ABC1D23): ");
            String placa = sc.nextLine().trim().toUpperCase();

            if (!placa.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) {
                throw new IllegalArgumentException("Placa inválida.");
            }

            Entregador novoEntregador = new Entregador(nome, cpf, telefone, placa);
            usuariosCadastrados.add(novoEntregador);
            System.out.println("Entregador cadastrado e disponível para entregas!");

        } catch (IllegalArgumentException erro) {
            System.out.println("Erro: " + erro.getMessage());

        } catch (Exception erro) {
            System.out.println("Erro inesperado: " + erro.getMessage());
        }
    }

    public void telaFazerPedido() {
        System.out.println("NOVO PEDIDO");
        try {
            System.out.print("CPF do Cliente: ");
            String cpf = sc.nextLine().trim();
            Usuario usuario = buscarUsuarioPorCpf(cpf);

            if (usuario == null) throw new IllegalArgumentException("Cliente não encontrado.");
            if (!(usuario instanceof Cliente)) throw new IllegalArgumentException("Esse CPF é de um entregador, não de um cliente.");

            Cliente cliente = (Cliente) usuario;

            listarCardapio();
            System.out.print("Código da pizza: ");
            int codigo = sc.nextInt();
            Pizza pizza = buscarPizzaPorCodigo(codigo);

            if (pizza == null) throw new IllegalArgumentException("Essa pizza não existe no cardápio.");

            System.out.print("Quantidade: ");
            int quantidade = sc.nextInt();

            if (quantidade <= 0 || quantidade > 20) {
                throw new IllegalArgumentException("Quantidade deve ser entre 1 e 20.");
            }

            System.out.print("1 - Delivery (+R$ 7,00) ou 2 - Retirar no balcão (10% off)? ");
            int tipo = sc.nextInt();
            sc.nextLine();

            Pedido pedido;

            if (tipo == 1) {
                Entregador entregador = buscarEntregadorDisponivel();

                if (entregador == null) {
                    throw new IllegalStateException("Todos os motoboys estão na rua. Tente retirada no balcão.");
                }

                entregador.setDisponivel(false);
                pedido = new PedidoDelivery(proximoNumeroPedido, cliente, pizza, quantidade, entregador, TAXA_ENTREGA);

            } else if (tipo == 2) {
                System.out.print("Observação (Ex: sem cebola): ");
                String obs = sc.nextLine();
                pedido = new PedidoRetirada(proximoNumeroPedido, cliente, pizza, quantidade, obs);

            } else {
                throw new IllegalArgumentException("Opção inválida (Digite 1 ou 2).");
            }

            pedidos.add(pedido);
            cliente.getPedidos().add(pedido);
            proximoNumeroPedido++;

            System.out.println("Pedido #" + pedido.getNumero() + " recebido!");
            System.out.println(pedido.getDescricao());
            System.out.printf("Total: R$ %.2f%n", pedido.calcularPrecoFinal());

        } catch (InputMismatchException erro) {
            System.out.println("Erro: Digite apenas números no código, quantidade e tipo.");
            sc.nextLine();

        } catch (IllegalArgumentException | IllegalStateException erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
    }

    public void telaMudarStatus() {
        System.out.println("ACOMPANHAR PEDIDOS");
        try {
            if (pedidos.isEmpty()) throw new IllegalStateException("Nenhum pedido feito ainda.");

            for (Pedido pedido : pedidos) {
                System.out.printf("#%d | %s | %-17s | R$ %.2f | %s%n",
                        pedido.getNumero(),
                        pedido.getDataHora().format(FORMATO_HORA),
                        pedido.getStatus(),
                        pedido.calcularPrecoFinal(),
                        pedido.getDescricao());
            }

            System.out.print("Número do pedido para avançar o status (0 para voltar): ");
            int numero = sc.nextInt();
            sc.nextLine();

            if (numero == 0) return;

            Pedido pedido = buscarPedidoPorNumero(numero);
            if (pedido == null) throw new IllegalArgumentException("Pedido #" + numero + " não encontrado.");

            String statusAntigo = pedido.getStatus();
            pedido.avancarStatus();

            System.out.println("Pedido #" + numero + ": " + statusAntigo + " -> " + pedido.getStatus());

            if (pedido.isFinalizado()) {
                System.out.println("Pedido finalizado. Bom apetite, " + pedido.getCliente().getNome() + "!");
            }

        } catch (InputMismatchException erro) {
            System.out.println("Erro: Digite apenas o número do pedido.");
            sc.nextLine();

        } catch (IllegalArgumentException | IllegalStateException erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
    }

    public void listarCardapio() {
        System.out.println("CARDAPIO - PIZZA QUE PARIU");
        for (Pizza pizza : cardapio) {
            System.out.println(pizza);
        }
    }

    private String lerCpfNovo() {
        System.out.print("CPF (só números): ");
        String cpf = sc.nextLine().trim();

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve ter 11 números.");
        }
        if (buscarUsuarioPorCpf(cpf) != null) {
            throw new IllegalArgumentException("Já existe alguém cadastrado com esse CPF.");
        }
        return cpf;
    }

    private String lerTelefone() {
        System.out.print("Telefone com DDD (só números): ");
        String telefone = sc.nextLine().trim();

        if (!telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone inválido.");
        }
        return telefone;
    }

    private Usuario buscarUsuarioPorCpf(String cpf) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }
        return null;
    }

    private Entregador buscarEntregadorDisponivel() {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario instanceof Entregador && ((Entregador) usuario).isDisponivel()) {
                return (Entregador) usuario;
            }
        }
        return null;
    }

    private Pizza buscarPizzaPorCodigo(int codigo) {
        for (Pizza pizza : cardapio) {
            if (pizza.getCodigo() == codigo) {
                return pizza;
            }
        }
        return null;
    }

    private Pedido buscarPedidoPorNumero(int numero) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumero() == numero) {
                return pedido;
            }
        }
        return null;
    }
}
