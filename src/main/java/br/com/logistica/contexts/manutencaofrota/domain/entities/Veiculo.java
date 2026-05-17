package br.com.logistica.contexts.manutencaofrota.domain.entities;

import br.com.logistica.sharedkernel.domain.valueobjects.Placa;

public class Veiculo {

    public enum Situacao { DISPONIVEL, EM_OPERACAO, EM_MANUTENCAO, BAIXADO }

    private final Placa placa;
    private final String modelo;
    private double quilometragemRodada;
    private Situacao situacao;
    private final double kmEntreRevisoes;
    private double kmDaUltimaRevisao;

    private Veiculo(Placa placa, String modelo, double quilometragemInicial, double kmEntreRevisoes) {
        this.placa = placa;
        this.modelo = modelo;
        this.quilometragemRodada = quilometragemInicial;
        this.situacao = Situacao.DISPONIVEL;
        this.kmEntreRevisoes = kmEntreRevisoes;
        this.kmDaUltimaRevisao = quilometragemInicial;
    }

    public static Veiculo cadastrar(Placa placa, String modelo,
                                     double quilometragemInicial, double kmEntreRevisoes) {
        if (quilometragemInicial < 0) {
            throw new IllegalArgumentException("Quilometragem inicial nao pode ser negativa");
        }
        if (kmEntreRevisoes <= 0) {
            throw new IllegalArgumentException("Intervalo entre revisoes deve ser positivo");
        }
        return new Veiculo(placa, modelo, quilometragemInicial, kmEntreRevisoes);
    }

    public void registrarUso(double kmRodados) {
        if (kmRodados < 0) {
            throw new IllegalArgumentException("Quilometragem rodada invalida");
        }
        this.quilometragemRodada += kmRodados;
    }

    public void entrarEmManutencao() {
        if (situacao == Situacao.BAIXADO) {
            throw new IllegalStateException("Veiculo baixado nao pode entrar em manutencao");
        }
        this.situacao = Situacao.EM_MANUTENCAO;
    }

    public void concluirManutencao() {
        if (situacao != Situacao.EM_MANUTENCAO) return;
        this.situacao = Situacao.DISPONIVEL;
        this.kmDaUltimaRevisao = this.quilometragemRodada;
    }

    public boolean precisaDeRevisao() {
        return (quilometragemRodada - kmDaUltimaRevisao) >= kmEntreRevisoes;
    }

    public Placa getPlaca()                  { return placa; }
    public String getModelo()                { return modelo; }
    public double getQuilometragemRodada()   { return quilometragemRodada; }
    public Situacao getSituacao()            { return situacao; }
}
