package br.com.logistica.contexts.faturamento.application.usecases;

import br.com.logistica.contexts.faturamento.domain.entities.Fatura;
import br.com.logistica.contexts.faturamento.domain.repositories.IFaturaRepository;
import br.com.logistica.sharedkernel.domain.identifiers.PedidoDeFreteId;
import br.com.logistica.sharedkernel.domain.valueobjects.Cnpj;
import br.com.logistica.sharedkernel.domain.valueobjects.Dinheiro;

import java.time.LocalDate;

public class EmitirCteAposEntrega {

    public record Entrada(
            String pedidoId,
            String cnpjTomador,
            double valorBruto,
            double iss,
            int vencimentoEmDias
    ) {}

    private final IFaturaRepository faturas;

    public EmitirCteAposEntrega(IFaturaRepository faturas) {
        this.faturas = faturas;
    }

    public Fatura executar(Entrada entrada) {
        LocalDate vencimento = LocalDate.now().plusDays(entrada.vencimentoEmDias());

        Fatura fatura = Fatura.emitir(
                PedidoDeFreteId.criar(entrada.pedidoId()),
                Cnpj.criar(entrada.cnpjTomador()),
                Dinheiro.criar(entrada.valorBruto()),
                Dinheiro.criar(entrada.iss()),
                vencimento
        );

        faturas.salvar(fatura);
        return fatura;
    }
}
