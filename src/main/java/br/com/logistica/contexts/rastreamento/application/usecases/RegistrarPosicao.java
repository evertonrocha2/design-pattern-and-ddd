package br.com.logistica.contexts.rastreamento.application.usecases;

import br.com.logistica.contexts.rastreamento.domain.entities.CargaEmTransito;
import br.com.logistica.contexts.rastreamento.domain.repositories.ICargaEmTransitoRepository;
import br.com.logistica.contexts.rastreamento.domain.valueobjects.Coordenada;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;

import java.time.Instant;

public class RegistrarPosicao {

    public record Entrada(
            String pedidoId,
            double latitude,
            double longitude,
            Instant momento
    ) {}

    private final ICargaEmTransitoRepository repositorio;

    public RegistrarPosicao(ICargaEmTransitoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void executar(Entrada entrada) {
        CargaEmTransito carga = repositorio
                .buscarPorPedido(PedidoDeFreteId.criar(entrada.pedidoId()))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Carga em transito nao encontrada para o pedido informado"));

        Instant momento = entrada.momento() == null ? Instant.now() : entrada.momento();
        carga.registrarPosicao(Coordenada.criar(entrada.latitude(), entrada.longitude()), momento);
        repositorio.salvar(carga);
    }
}
