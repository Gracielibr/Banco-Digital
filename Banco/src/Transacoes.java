import java.util.Scanner;

public class Transacoes {

    public static void menuOperacoes(Conta conta) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n---- Menu da Conta ----");
            System.out.println("1. Depósito\n2. Saque\n3. Transferência\n4. Ver Saldo\n5. Sair");
            System.out.print("Escolha a operação: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o valor para depósito: ");
                    double valor = Double.parseDouble(scanner.nextLine());
                    conta.depositar(valor);
                    System.out.println("Saldo atual: R$ " + String.format("%.2f", conta.getSaldo()));
                }
                case 2 -> {
                    System.out.print("Digite o valor para saque: ");
                    double valor = Double.parseDouble(scanner.nextLine());
                    conta.sacar(valor);
                }
                case 3 -> {
                    Conta contaDestino = buscarContaDestino(scanner);
                    if (contaDestino != null) {
                        System.out.print("Digite o valor da transferência: ");
                        double valor = Double.parseDouble(scanner.nextLine());
                        conta.transferir(contaDestino, valor);
                    }
                }
                case 4 -> System.out.println("Saldo atual: R$ " + conta.getSaldo());
                case 5 -> {
                    System.out.print("Tem certeza que deseja sair da conta? (s para sair ou n para acessar outra conta): ");
                    String resposta = scanner.nextLine().trim().toLowerCase();
                    if (resposta.equals("s") || resposta.equals("sim")) {
                        System.out.println("Saindo da conta...");
                        return;
                    } else if (resposta.equals("n") || resposta.equals("não")) {
                        System.out.println("Retornando ao menu para acessar outra conta...");
                        Conta.acessarConta();
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static Conta buscarContaDestino(Scanner scanner) {
        while (true) {
            System.out.print("Digite o número da agência da conta de destino: ");
            String agencia = scanner.nextLine();
            if (!agencia.equals("0001")) {
                System.out.println("Este banco possui somente a agência 0001. Digite novamente.");
                continue;
            }

            System.out.print("Digite o número da conta de destino: ");
            String numero = scanner.nextLine();

            for (Cliente cliente : Cliente.clientes) {
                for (Conta c : cliente.getContas()) {
                    if (c.getAgencia().equals(agencia) && c.getNumero().equals(numero)) {
                        return c;
                    }
                }
            }

            System.out.println("Conta de destino não encontrada.");
            System.out.print("Deseja ver a lista de todos os clientes? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s")) Cliente.exibirTodosClientes();

            System.out.println("Tente novamente.\n");
        }
    }
}