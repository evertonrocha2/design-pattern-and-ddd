package br.com.logistica.contexts.gestaofretes.domain.valueobjects;

public final class Carga {

    public record Dimensoes(double alturaEmCm, double larguraEmCm, double profundidadeEmCm) {}

    private final String descricao;
    private final double pesoEmKg;
    private final Dimensoes dimensoes;
    private final boolean fragil;

    private Carga(String descricao, double pesoEmKg, Dimensoes dimensoes, boolean fragil) {
        this.descricao = descricao;
        this.pesoEmKg = pesoEmKg;
        this.dimensoes = dimensoes;
        this.fragil = fragil;
    }

    public static Carga criar(String descricao, double pesoEmKg, Dimensoes dimensoes) {
        return criar(descricao, pesoEmKg, dimensoes, false);
    }

    public static Carga criar(String descricao, double pesoEmKg, Dimensoes dimensoes, boolean fragil) {
        if (pesoEmKg <= 0) {
            throw new IllegalArgumentException("Peso da carga deve ser positivo");
        }
        return new Carga(descricao, pesoEmKg, dimensoes, fragil);
    }

    public String getDescricao()     { return descricao; }
    public double getPesoEmKg()      { return pesoEmKg; }
    public Dimensoes getDimensoes()  { return dimensoes; }
    public boolean isFragil()        { return fragil; }
}
