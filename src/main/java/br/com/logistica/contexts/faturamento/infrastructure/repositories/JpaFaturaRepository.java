package br.com.logistica.contexts.faturamento.infrastructure.repositories;

import br.com.logistica.contexts.faturamento.domain.entities.Fatura;
import br.com.logistica.contexts.faturamento.domain.repositories.IFaturaRepository;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.util.List;
import java.util.Optional;

public class JpaFaturaRepository implements IFaturaRepository {

    @Override
    public void salvar(Fatura fatura) {
        throw new UnsupportedOperationException("Implementar persistencia com JPA");
    }

    @Override
    public Optional<Fatura> buscarPorPedido(PedidoDeFreteId pedidoId) {
        throw new UnsupportedOperationException("Implementar busca com JPA");
    }

    @Override
    public List<Fatura> listarInadimplentes() {
        throw new UnsupportedOperationException("Implementar listagem com JPA");
    }
}
