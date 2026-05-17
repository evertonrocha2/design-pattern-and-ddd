package br.com.logistica.contexts.rastreamento.domain.entities;

import br.com.logistica.contexts.rastreamento.domain.valueobjects.Coordenada;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;
import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CargaEmTransito {

    public enum Status { AGUARDANDO_COLETA, COLETADA, EM_TRANSITO, ENTREGUE }

    public record EventoPosicao(Coordenada posicao, Instant registradoEm) {}

    private final PedidoDeFreteId pedidoId;
    private final List<EventoPosicao> historico = new ArrayList<>();
    private Placa veiculo;
    private Status status;

    private CargaEmTransito(PedidoDeFreteId pedidoId) {
        this.pedidoId = pedidoId;
        this.status = Status.AGUARDANDO_COLETA;
    }

    public static CargaEmTransito iniciar(PedidoDeFreteId pedidoId) {
        return new CargaEmTransito(pedidoId);
    }

    public void vincularVeiculo(Placa placa) {
        this.veiculo = placa;
    }

    public void registrarPosicao(Coordenada posicao, Instant momento) {
        if (status == Status.ENTREGUE) return;
        historico.add(new EventoPosicao(posicao, momento));
        if (status == Status.AGUARDANDO_COLETA) {
            this.status = Status.COLETADA;
        } else if (status == Status.COLETADA) {
            this.status = Status.EM_TRANSITO;
        }
    }

    public void confirmarEntrega() {
        if (status != Status.EM_TRANSITO && status != Status.COLETADA) {
            throw new IllegalStateException("Entrega so pode ser confirmada apos coleta");
        }
        this.status = Status.ENTREGUE;
    }

    public PedidoDeFreteId getPedidoId() { return pedidoId; }
    public Status getStatus()            { return status; }
    public Placa getVeiculo()            { return veiculo; }
    public List<EventoPosicao> getHistorico() { return Collections.unmodifiableList(historico); }

    public EventoPosicao getUltimaPosicao() {
        if (historico.isEmpty()) return null;
        return historico.get(historico.size() - 1);
    }
}
