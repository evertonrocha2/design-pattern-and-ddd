package br.com.logistica.contexts.manutencaofrota.infrastructure.repositories;

import br.com.logistica.contexts.manutencaofrota.domain.entities.Veiculo;
import br.com.logistica.contexts.manutencaofrota.domain.repositories.IVeiculoRepository;
import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

import java.util.List;
import java.util.Optional;

public class JpaVeiculoRepository implements IVeiculoRepository {

    @Override
    public void salvar(Veiculo veiculo) {
        throw new UnsupportedOperationException("Implementar persistencia com JPA");
    }

    @Override
    public Optional<Veiculo> buscarPorPlaca(Placa placa) {
        throw new UnsupportedOperationException("Implementar busca com JPA");
    }

    @Override
    public List<Veiculo> listarComRevisaoPendente() {
        throw new UnsupportedOperationException("Implementar listagem com JPA");
    }
}
