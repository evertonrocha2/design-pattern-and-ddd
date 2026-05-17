package br.com.logistica.contexts.gestaofretes.domain.events;

import br.com.logistica.contexts.gestaofretes.domain.valueobjects.Modal;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.time.Instant;

public record PedidoCriado(
        PedidoDeFreteId pedidoId,
        Modal.Tipo modal,
        Instant criadoEm
) {
    public String nome() {
        return "PedidoCriado";
    }
}
