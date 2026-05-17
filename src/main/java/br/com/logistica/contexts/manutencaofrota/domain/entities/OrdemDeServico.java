package br.com.logistica.contexts.manutencaofrota.domain.entities;

import br.com.logistica.sharedkernel.domain.valueobjects.Dinheiro;
import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

import java.time.Instant;

public class OrdemDeServico {

    public enum Tipo { PREVENTIVA, CORRETIVA }

    public enum Status { ABERTA, EM_EXECUCAO, CONCLUIDA, CANCELADA }

    private final String numero;
    private final Placa veiculo;
    private final Tipo tipo;
    private final String descricao;
    private Status status;
    private Dinheiro custo;
    private final Instant abertura;
    private Instant conclusao;

    private OrdemDeServico(String numero, Placa veiculo, Tipo tipo, String descricao) {
        this.numero = numero;
        this.veiculo = veiculo;
        this.tipo = tipo;
        this.descricao = descricao;
        this.status = Status.ABERTA;
        this.custo = Dinheiro.criar(0);
        this.abertura = Instant.now();
        this.conclusao = null;
    }

    public static OrdemDeServico abrir(String numero, Placa veiculo, Tipo tipo, String descricao) {
        return new OrdemDeServico(numero, veiculo, tipo, descricao);
    }

    public void iniciarExecucao() {
        if (status != Status.ABERTA) {
            throw new IllegalStateException("So e possivel iniciar OS aberta");
        }
        this.status = Status.EM_EXECUCAO;
    }

    public void concluir(Dinheiro custoFinal) {
        if (status != Status.EM_EXECUCAO) {
            throw new IllegalStateException("So e possivel concluir OS em execucao");
        }
        this.custo = custoFinal;
        this.status = Status.CONCLUIDA;
        this.conclusao = Instant.now();
    }

    public void cancelar() {
        if (status == Status.CONCLUIDA) {
            throw new IllegalStateException("OS concluida nao pode ser cancelada");
        }
        this.status = Status.CANCELADA;
    }

    public String getNumero()         { return numero; }
    public Placa getVeiculo()         { return veiculo; }
    public Tipo getTipo()             { return tipo; }
    public String getDescricao()      { return descricao; }
    public Status getStatus()         { return status; }
    public Dinheiro getCusto()        { return custo; }
    public Instant getAbertura()      { return abertura; }
    public Instant getConclusao()     { return conclusao; }
}
