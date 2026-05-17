package br.com.logistica.contexts.gestaofretes.domain.entities;

import br.com.logistica.contexts.gestaofretes.domain.valueobjects.Carga;
import br.com.logistica.contexts.gestaofretes.domain.valueobjects.Modal;
import br.com.logistica.contexts.gestaofretes.domain.valueobjects.Rota;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;
import br.com.logistica.sharedkernel.domain.valueobjects.Dinheiro;
import br.com.logistica.sharedkernel.domain.valueobjects.Endereco;
import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

public class PedidoDeFrete {

    public enum Status { CRIADO, VEICULO_ALOCADO, EM_TRANSITO, ENTREGUE, CANCELADO }

    private final PedidoDeFreteId id;
    private final Endereco origem;
    private final Endereco destino;
    private final Carga carga;
    private final Modal modal;
    private final Rota rota;
    private final Dinheiro valor;
    private Status status;
    private Placa veiculoAlocado;

    private PedidoDeFrete(PedidoDeFreteId id, Endereco origem, Endereco destino,
                          Carga carga, Modal modal, Rota rota, Dinheiro valor) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.carga = carga;
        this.modal = modal;
        this.rota = rota;
        this.valor = valor;
        this.status = Status.CRIADO;
        this.veiculoAlocado = null;
    }

    public static PedidoDeFrete criar(Endereco origem, Endereco destino, Carga carga,
                                       Modal modal, Rota rota, Dinheiro valor) {
        return new PedidoDeFrete(PedidoDeFreteId.novo(), origem, destino, carga, modal, rota, valor);
    }

    public void alocarVeiculo(Placa placa) {
        if (status != Status.CRIADO) {
            throw new IllegalStateException("So e possivel alocar veiculo em pedido recem-criado");
        }
        this.veiculoAlocado = placa;
        this.status = Status.VEICULO_ALOCADO;
    }

    public void cancelar() {
        if (status == Status.ENTREGUE) {
            throw new IllegalStateException("Pedido ja entregue nao pode ser cancelado");
        }
        this.status = Status.CANCELADO;
    }

    public PedidoDeFreteId getId()        { return id; }
    public Status getStatus()             { return status; }
    public Placa getVeiculoAlocado()      { return veiculoAlocado; }
    public Modal getModal()               { return modal; }
    public Dinheiro getValor()            { return valor; }
    public Endereco getOrigem()           { return origem; }
    public Endereco getDestino()          { return destino; }
    public Carga getCarga()               { return carga; }
    public Rota getRota()                 { return rota; }
}
