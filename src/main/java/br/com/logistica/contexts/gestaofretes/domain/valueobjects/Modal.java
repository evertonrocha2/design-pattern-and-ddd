package br.com.logistica.contexts.gestaofretes.domain.valueobjects;

public final class Modal {

    public enum Tipo { MOTOBOY, CAMINHAO, TREM, NAVIO }

    private final Tipo tipo;

    private Modal(Tipo tipo) {
        this.tipo = tipo;
    }

    public static Modal motoboy()  { return new Modal(Tipo.MOTOBOY); }
    public static Modal caminhao() { return new Modal(Tipo.CAMINHAO); }
    public static Modal trem()     { return new Modal(Tipo.TREM); }
    public static Modal navio()    { return new Modal(Tipo.NAVIO); }
    public static Modal criar(Tipo tipo) { return new Modal(tipo); }

    public Tipo getTipo() { return tipo; }

    public boolean suportaPesoEmKg(double pesoKg) {
        double limite = switch (tipo) {
            case MOTOBOY  -> 30;
            case CAMINHAO -> 30_000;
            case TREM     -> 1_000_000;
            case NAVIO    -> 50_000_000;
        };
        return pesoKg <= limite;
    }
}
