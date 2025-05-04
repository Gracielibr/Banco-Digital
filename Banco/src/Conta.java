import java.util.Scanner;

public abstract class Conta {
    protected double saldo;
    protected Cliente cliente;
    protected String numero;
    protected String agencia;

    public Conta(Cliente cliente, String numero, String agencia) {
        this.cliente = cliente;
        this.numero = numero;
        this.agencia = agencia;
    }

    public static void acessarConta() {
        Scanner scanner = new Scanner(System.in);
        Cliente cliente = null;

        while (cliente == null) {
            System.out.print("Digite seu CPF: ");
            String cpf = scanner.nextLine();
            for (Cliente c : Cliente.clientes) {
                if (c.getCpf().equals(cpf)) {
                    cliente = c;
                    break;
                }
            }
            if (cliente == null) System.out.println("Cliente não encontrado. Tente novamente.");
        }

        System.out.println("Escolha a conta que deseja acessar:");
        System.out.println("1 - Conta Corrente\n2 - Conta Poupança");
        int tipoConta = Integer.parseInt(scanner.nextLine());

        Conta conta = null;
        do {
            do {
                System.out.print("Digite o número da agência: ");
                String agencia = scanner.nextLine();
                if (!agencia.equals("0001"))
                    System.out.println("Este banco possui somente a agência 0001.");
                else break;
            } while (true);

            System.out.print("Digite o número da conta: ");
            String numero = scanner.nextLine();
            conta = buscarConta(cliente, tipoConta, "0001", numero);

            if (conta == null) {
                System.out.println("\nConta não encontrada.");
                System.out.println("===============================================");
                System.out.printf("Cliente: %s | CPF: %s\n", cliente.getNome(), cliente.getCpf());
                for (Conta c : cliente.getContas()) {
                    if ((tipoConta == 1 && c instanceof contaCorrente) ||
                        (tipoConta == 2 && c instanceof contaPoupanca)) {
                        System.out.printf("Agência: %s | Conta: %s\n", c.getAgencia(), c.getNumero());
                    }
                }
                System.out.println("===============================================\nTente novamente.");
            }
        } while (conta == null);

        System.out.println("Acesso à conta realizado com sucesso!");
        conta.menuOperacoes();
    }

    private static Conta buscarConta(Cliente cliente, int tipo, String agencia, String numero) {
        for (Conta c : cliente.getContas()) {
            boolean tipoValido = (tipo == 1 && c instanceof contaCorrente) || (tipo == 2 && c instanceof contaPoupanca);
            if (tipoValido && c.getAgencia().equals(agencia) && c.getNumero().equals(numero)) return c;
        }
        return null;
    }

    public String getNumero() { return numero; }
    public String getAgencia() { return agencia; }
    public double getSaldo() { return saldo; }

    public abstract void menuOperacoes();

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.printf("Depósito de R$ %.2f realizado com sucesso.\n", valor);
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.printf("Saque de R$ %.2f realizado com sucesso.\n", valor);
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
        }
        System.out.printf("Saldo atual: R$ %.2f\n", saldo);
    }

    public void transferir(Conta destino, double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            destino.depositar(valor);
            System.out.printf("Transferência de R$ %.2f para %s realizada com sucesso.\n",
                    valor, destino.cliente.getNome());
            System.out.printf("Saldo atual: R$ %.2f\n", saldo);
        } else {
            System.out.println("Transferência não realizada. Saldo insuficiente ou valor inválido.");
        }
    }
}