package br.com.logistica.contexts.gestaofretes.infrastructure.repositories;

import br.com.logistica.contexts.gestaofretes.domain.entities.PedidoDeFrete;
import br.com.logistica.contexts.gestaofretes.domain.repositories.IPedidoDeFreteRepository;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.util.List;
import java.util.Optional;

public class JpaPedidoDeFreteRepository implements IPedidoDeFreteRepository {

    @Override
    public void salvar(PedidoDeFrete pedido) {
        throw new UnsupportedOperationException("Implementar persistencia com JPA");
    }

    @Override
    public Optional<PedidoDeFrete> buscarPorId(PedidoDeFreteId id) {
        throw new UnsupportedOperationException("Implementar busca com JPA");
    }

    @Override
    public List<PedidoDeFrete> listarPendentesDeAlocacao() {
        throw new UnsupportedOperationException("Implementar listagem com JPA");
    }
}
