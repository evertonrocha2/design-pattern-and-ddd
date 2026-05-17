package br.com.logistica.contexts.manutencaofrota.infrastructure.repositories;

import br.com.logistica.contexts.manutencaofrota.domain.entities.OrdemDeServico;
import br.com.logistica.contexts.manutencaofrota.domain.repositories.IOrdemDeServicoRepository;
import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

import java.util.List;
import java.util.Optional;

public class JpaOrdemDeServicoRepository implements IOrdemDeServicoRepository {

    @Override
    public void salvar(OrdemDeServico ordem) {
        throw new UnsupportedOperationException("Implementar persistencia com JPA");
    }

    @Override
    public Optional<OrdemDeServico> buscarPorNumero(String numero) {
        throw new UnsupportedOperationException("Implementar busca com JPA");
    }

    @Override
    public List<OrdemDeServico> listarAbertasPorVeiculo(Placa placa) {
        throw new UnsupportedOperationException("Implementar listagem com JPA");
    }
}
