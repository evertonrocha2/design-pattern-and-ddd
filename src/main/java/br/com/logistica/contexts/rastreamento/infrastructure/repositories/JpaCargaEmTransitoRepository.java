package br.com.logistica.contexts.rastreamento.infrastructure.repositories;

import br.com.logistica.contexts.rastreamento.domain.entities.CargaEmTransito;
import br.com.logistica.contexts.rastreamento.domain.repositories.ICargaEmTransitoRepository;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.util.Optional;

public class JpaCargaEmTransitoRepository implements ICargaEmTransitoRepository {

    @Override
    public void salvar(CargaEmTransito carga) {
        throw new UnsupportedOperationException("Implementar persistencia com JPA");
    }

    @Override
    public Optional<CargaEmTransito> buscarPorPedido(PedidoDeFreteId pedidoId) {
        throw new UnsupportedOperationException("Implementar busca com JPA");
    }
}
