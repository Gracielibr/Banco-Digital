public class contaPoupanca extends Conta {

    public contaPoupanca(Cliente cliente, String numero, String agencia) {
        super(cliente, numero, agencia);
    }
    public void menuOperacoes() {
        Transacoes.menuOperacoes(this);
    }
}
