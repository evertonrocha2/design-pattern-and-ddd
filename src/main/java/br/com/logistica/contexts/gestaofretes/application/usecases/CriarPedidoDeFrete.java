package br.com.logistica.contexts.gestaofretes.application.usecases;

import br.com.logistica.contexts.gestaofretes.domain.entities.PedidoDeFrete;
import br.com.logistica.contexts.gestaofretes.domain.repositories.IPedidoDeFreteRepository;
import br.com.logistica.contexts.gestaofretes.domain.valueobjects.Carga;
import br.com.logistica.contexts.gestaofretes.domain.valueobjects.Modal;
import br.com.logistica.contexts.gestaofretes.domain.valueobjects.Rota;
import br.com.logistica.sharedkernel.domain.valueobjects.Dinheiro;
import br.com.logistica.sharedkernel.domain.valueobjects.Endereco;

public class CriarPedidoDeFrete {

    public record Entrada(
            Endereco origem,
            Endereco destino,
            double cargaPesoKg,
            String cargaDescricao,
            Modal.Tipo modal,
            double distanciaKm,
            double valorCotado
    ) {}

    private final IPedidoDeFreteRepository repositorio;

    public CriarPedidoDeFrete(IPedidoDeFreteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public PedidoDeFrete executar(Entrada entrada) {
        Modal modal = Modal.criar(entrada.modal());

        if (!modal.suportaPesoEmKg(entrada.cargaPesoKg())) {
            throw new IllegalArgumentException(
                    "Modal " + entrada.modal() + " nao suporta carga de "
                            + entrada.cargaPesoKg() + "kg"
            );
        }

        Carga carga = Carga.criar(
                entrada.cargaDescricao(),
                entrada.cargaPesoKg(),
                new Carga.Dimensoes(0, 0, 0)
        );

        Rota rota = Rota.calcular(entrada.origem(), entrada.destino(), entrada.distanciaKm());

        PedidoDeFrete pedido = PedidoDeFrete.criar(
                entrada.origem(),
                entrada.destino(),
                carga,
                modal,
                rota,
                Dinheiro.criar(entrada.valorCotado())
        );

        repositorio.salvar(pedido);
        return pedido;
    }
}
