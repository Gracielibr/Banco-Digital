public class contaCorrente extends Conta {

    public contaCorrente(Cliente cliente, String numero, String agencia) {
        super(cliente, numero, agencia);
    }
    public void menuOperacoes() {
            Transacoes.menuOperacoes(this);
    }
}