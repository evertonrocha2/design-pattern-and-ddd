package br.com.logistica.contexts.rastreamento.domain.events;

import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.time.Instant;

public record EntregaConfirmada(
        PedidoDeFreteId pedidoId,
        Instant confirmadaEm
) {
    public String nome() {
        return "EntregaConfirmada";
    }
}
