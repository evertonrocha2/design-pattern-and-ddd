package br.com.logistica.contexts.rastreamento.domain.repositories;

import br.com.logistica.contexts.rastreamento.domain.entities.CargaEmTransito;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.util.Optional;

public interface ICargaEmTransitoRepository {

    void salvar(CargaEmTransito carga);

    Optional<CargaEmTransito> buscarPorPedido(PedidoDeFreteId pedidoId);
}
