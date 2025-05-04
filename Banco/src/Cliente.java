import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private List<Conta> contas;

    private static int proximoNumeroConta = 1;
    public static List<Cliente> clientes = new ArrayList<>();
    public static List<Conta> todasContas = new ArrayList<>();
    private static int clientesCriados = 0;

    static { inicializarClientesPadrao(); }

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }

    public static void inicializarClientesPadrao() {
        if (!clientes.isEmpty()) return;

        String[][] dadosClientes = {
            {"João Silva", "11"}, {"Maria Souza", "22"},
            {"Carlos Lima", "33"}, {"Ana Costa", "44"},
            {"Paulo Oliveira", "55"}
        };

        for (String[] dados : dadosClientes) {
            Cliente cliente = new Cliente(dados[0], dados[1]);
            Conta cc = new contaCorrente(cliente, gerarNumeroConta(), "0001");
            Conta cp = new contaPoupanca(cliente, gerarNumeroConta(), "0001");
            cliente.contas.addAll(List.of(cc, cp));
            clientes.add(cliente);
            todasContas.addAll(List.of(cc, cp));
        }
    }

    private static String gerarNumeroConta() {
        return String.format("%05d", proximoNumeroConta++);
    }

    public static void novoCliente() {
        if (clientesCriados >= 5) {
            System.out.println("Limite de novos clientes atingido.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o seu nome e sobrenome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o seu CPF: ");
        String cpf = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, cpf);
        Conta cc = new contaCorrente(novoCliente, gerarNumeroConta(), "0001");
        Conta cp = new contaPoupanca(novoCliente, gerarNumeroConta(), "0001");
        novoCliente.contas.addAll(List.of(cc, cp));
        clientes.add(novoCliente);
        todasContas.addAll(List.of(cc, cp));
        clientesCriados++;

        System.out.println("\nDados das suas novas contas:");
        System.out.println("========================================");
        System.out.printf("Nome: %s\nCPF: %s\nContas do cliente:\n", nome, cpf);
        for (Conta conta : novoCliente.contas) {
            String tipo = (conta instanceof contaCorrente) ? "Conta Corrente" : "Conta Poupança";
            System.out.printf("%s - Agência: %s | Número: %s\n", tipo, conta.getAgencia(), conta.getNumero());
        }
        System.out.println("========================================\nSua conta foi criada com sucesso!");

        System.out.print("Deseja acessar sua conta agora? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        if (resposta.equals("s") || resposta.equals("sim")) {
            System.out.println("Acessando a conta...");
            Conta.acessarConta();
        } else {
            System.out.println("Você pode acessar sua conta mais tarde.");
        }
    }

    public void exibirInformacoesCliente() {
        System.out.println("====================================");
        System.out.printf("Cliente: %s\nCPF: %s\nContas:\n", nome, cpf);
        contas.forEach(conta -> {
            String tipo = (conta instanceof contaCorrente) ? "Conta Corrente" : "Conta Poupança";
            System.out.printf("%s | Agência: %s | Conta: %s\n", tipo, conta.getAgencia(), conta.getNumero());
        });
    }

    public static void exibirTodosClientes() {
        System.out.println("Lista de Clientes:");
        clientes.forEach(Cliente::exibirInformacoesCliente);
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public List<Conta> getContas() { return contas; }
}