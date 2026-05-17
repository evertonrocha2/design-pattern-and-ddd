package br.com.logistica.contexts.manutencaofrota.domain.repositories;

import br.com.logistica.contexts.manutencaofrota.domain.entities.Veiculo;
import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

import java.util.List;
import java.util.Optional;

public interface IVeiculoRepository {

    void salvar(Veiculo veiculo);

    Optional<Veiculo> buscarPorPlaca(Placa placa);

    List<Veiculo> listarComRevisaoPendente();
}
