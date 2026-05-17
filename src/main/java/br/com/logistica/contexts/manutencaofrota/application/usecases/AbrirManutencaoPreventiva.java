package br.com.logistica.contexts.manutencaofrota.application.usecases;

import br.com.logistica.contexts.manutencaofrota.domain.entities.OrdemDeServico;
import br.com.logistica.contexts.manutencaofrota.domain.entities.Veiculo;
import br.com.logistica.contexts.manutencaofrota.domain.repositories.IOrdemDeServicoRepository;
import br.com.logistica.contexts.manutencaofrota.domain.repositories.IVeiculoRepository;
import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

public class AbrirManutencaoPreventiva {

    public record Entrada(String placa, String descricao) {}

    private final IVeiculoRepository veiculos;
    private final IOrdemDeServicoRepository ordens;

    public AbrirManutencaoPreventiva(IVeiculoRepository veiculos, IOrdemDeServicoRepository ordens) {
        this.veiculos = veiculos;
        this.ordens = ordens;
    }

    public OrdemDeServico executar(Entrada entrada) {
        Placa placa = Placa.criar(entrada.placa());

        Veiculo veiculo = veiculos.buscarPorPlaca(placa)
                .orElseThrow(() -> new IllegalArgumentException("Veiculo nao encontrado"));

        if (!veiculo.precisaDeRevisao()) {
            throw new IllegalStateException("Veiculo ainda nao atingiu o intervalo de revisao");
        }

        veiculo.entrarEmManutencao();
        veiculos.salvar(veiculo);

        OrdemDeServico ordem = OrdemDeServico.abrir(
                "OS-" + System.currentTimeMillis(),
                placa,
                OrdemDeServico.Tipo.PREVENTIVA,
                entrada.descricao()
        );
        ordens.salvar(ordem);
        return ordem;
    }
}
