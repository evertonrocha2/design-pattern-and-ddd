package br.com.logistica.contexts.faturamento.domain.entities;

import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;
import br.com.logistica.sharedkernel.domain.valueobjects.Cnpj;
import br.com.logistica.sharedkernel.domain.valueobjects.Dinheiro;

import java.time.LocalDate;

public class Fatura {

    public enum Situacao { EMITIDA, PAGA, INADIMPLENTE, CANCELADA }

    private final PedidoDeFreteId pedidoId;
    private final Cnpj tomador;
    private final Dinheiro valor;
    private final Dinheiro impostoRetido;
    private final LocalDate emissao;
    private final LocalDate vencimento;
    private Situacao situacao;

    private Fatura(PedidoDeFreteId pedidoId, Cnpj tomador, Dinheiro valor,
                   Dinheiro impostoRetido, LocalDate emissao, LocalDate vencimento) {
        this.pedidoId = pedidoId;
        this.tomador = tomador;
        this.valor = valor;
        this.impostoRetido = impostoRetido;
        this.emissao = emissao;
        this.vencimento = vencimento;
        this.situacao = Situacao.EMITIDA;
    }

    public static Fatura emitir(PedidoDeFreteId pedidoId, Cnpj tomador, Dinheiro valor,
                                 Dinheiro impostoRetido, LocalDate vencimento) {
        return new Fatura(pedidoId, tomador, valor, impostoRetido, LocalDate.now(), vencimento);
    }

    public void marcarComoPaga(LocalDate dataPagamento) {
        if (situacao != Situacao.EMITIDA && situacao != Situacao.INADIMPLENTE) {
            throw new IllegalStateException("Fatura nao pode ser marcada como paga no estado atual");
        }
        if (dataPagamento.isBefore(emissao)) {
            throw new IllegalArgumentException("Data de pagamento anterior a emissao");
        }
        this.situacao = Situacao.PAGA;
    }

    public void marcarInadimplencia() {
        if (situacao != Situacao.EMITIDA) return;
        this.situacao = Situacao.INADIMPLENTE;
    }

    public void cancelar() {
        if (situacao == Situacao.PAGA) {
            throw new IllegalStateException("Fatura paga nao pode ser cancelada");
        }
        this.situacao = Situacao.CANCELADA;
    }

    public PedidoDeFreteId getPedidoId() { return pedidoId; }
    public Cnpj getTomador()             { return tomador; }
    public Dinheiro getValor()           { return valor; }
    public Dinheiro getImpostoRetido()   { return impostoRetido; }
    public LocalDate getEmissao()        { return emissao; }
    public LocalDate getVencimento()     { return vencimento; }
    public Situacao getSituacao()        { return situacao; }
}
