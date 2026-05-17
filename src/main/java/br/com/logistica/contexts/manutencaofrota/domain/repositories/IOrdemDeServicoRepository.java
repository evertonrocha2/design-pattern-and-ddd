package br.com.logistica.contexts.manutencaofrota.domain.repositories;

import br.com.logistica.contexts.manutencaofrota.domain.entities.OrdemDeServico;
import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

import java.util.List;
import java.util.Optional;

public interface IOrdemDeServicoRepository {

    void salvar(OrdemDeServico ordem);

    Optional<OrdemDeServico> buscarPorNumero(String numero);

    List<OrdemDeServico> listarAbertasPorVeiculo(Placa placa);
}
