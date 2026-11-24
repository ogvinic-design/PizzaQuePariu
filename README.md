# Pizza Que Pariu - Sistema de Delivery

Projeto da disciplina de Programação Orientada a Objetos (Java).
O sistema serve para cadastrar clientes e entregadores, receber pedidos de pizza e acompanhar o status de cada um.

O que o código faz:
- Cadastra Clientes e Entregadores (Herança da classe abstrata Usuario).
- Mostra o cardápio de pizzas da casa.
- Faz pedidos Delivery (com taxa e motoboy) ou Retirada no balcão (com 10% de desconto). Polimorfismo com a interface Entregavel e a classe abstrata Pedido.
- Cada tipo de pedido tem seu próprio caminho de status (RECEBIDO -> NO FORNO -> SAIU PARA ENTREGA -> ENTREGUE ou PRONTO NO BALCAO -> RETIRADO).
- O motoboy fica ocupado durante a entrega e volta a ficar disponível quando o pedido é entregue.
- Tratamento de exceções para CPF, telefone, placa, quantidade e opções inválidas.

Banco de dados:
O script `banco/banco_pizzaria_delivery.sql` cria o banco no MySQL Workbench com as tabelas cliente, entregador, pizza e pedido, já com alguns dados de exemplo.

Como rodar:
Abra o projeto no IntelliJ e execute a classe PizzaQuePariu.java.

Autores: José Vinícius Soares Vieira Costa; Gabriel valenca de Lima
