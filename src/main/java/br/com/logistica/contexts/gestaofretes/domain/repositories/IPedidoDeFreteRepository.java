package br.com.logistica.contexts.gestaofretes.domain.repositories;

import br.com.logistica.contexts.gestaofretes.domain.entities.PedidoDeFrete;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.util.List;
import java.util.Optional;

public interface IPedidoDeFreteRepository {

    void salvar(PedidoDeFrete pedido);

    Optional<PedidoDeFrete> buscarPorId(PedidoDeFreteId id);

    List<PedidoDeFrete> listarPendentesDeAlocacao();
}
