package br.com.logistica.contexts.faturamento.domain.repositories;

import br.com.logistica.contexts.faturamento.domain.entities.Fatura;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.util.List;
import java.util.Optional;

public interface IFaturaRepository {

    void salvar(Fatura fatura);

    Optional<Fatura> buscarPorPedido(PedidoDeFreteId pedidoId);

    List<Fatura> listarInadimplentes();
}
