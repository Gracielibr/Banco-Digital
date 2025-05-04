import java.util.Scanner;

public class Banco {
    public static void main(String[] args) {
        Cliente.inicializarClientesPadrao();
        System.out.println("Olá! É muito bom te ver por aqui, no seu Banco GR");
        Cliente.exibirTodosClientes();

        Scanner scanner = new Scanner(System.in);
        String opcao;

        do {
            System.out.print("Digite 'a' para acessar a sua conta ou 'c' pra criar uma conta: ");
            opcao = scanner.nextLine().trim().toLowerCase();

            switch (opcao) {
                case "a":
                    Conta.acessarConta();
                    break;
                case "c":
                    Cliente.novoCliente();
                    break;
                default:
                    System.out.println("Escolha uma opção válida.");
            }
        } while (!opcao.equals("a") && !opcao.equals("c"));

        scanner.close();
    }
}