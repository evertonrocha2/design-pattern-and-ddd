package br.com.logistica.sharedkernel.domain.valueobjects;

import java.util.Objects;

public final class Dinheiro {

    public enum Moeda { BRL, USD, EUR }

    private final long valorEmCentavos;
    private final Moeda moeda;

    private Dinheiro(long valorEmCentavos, Moeda moeda) {
        this.valorEmCentavos = valorEmCentavos;
        this.moeda = moeda;
    }

    public static Dinheiro criar(double valor) {
        return criar(valor, Moeda.BRL);
    }

    public static Dinheiro criar(double valor, Moeda moeda) {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor < 0) {
            throw new IllegalArgumentException("Valor monetario invalido");
        }
        return new Dinheiro(Math.round(valor * 100), moeda);
    }

    public Dinheiro somar(Dinheiro outro) {
        garantirMesmaMoeda(outro);
        return new Dinheiro(valorEmCentavos + outro.valorEmCentavos, moeda);
    }

    public Dinheiro subtrair(Dinheiro outro) {
        garantirMesmaMoeda(outro);
        long resultado = valorEmCentavos - outro.valorEmCentavos;
        if (resultado < 0) {
            throw new IllegalStateException("Subtracao resultaria em valor negativo");
        }
        return new Dinheiro(resultado, moeda);
    }

    public double getValor() { return valorEmCentavos / 100.0; }
    public Moeda getMoeda() { return moeda; }

    private void garantirMesmaMoeda(Dinheiro outro) {
        if (moeda != outro.moeda) {
            throw new IllegalArgumentException("Operacao entre moedas diferentes nao e permitida");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dinheiro that)) return false;
        return valorEmCentavos == that.valorEmCentavos && moeda == that.moeda;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valorEmCentavos, moeda);
    }
}
