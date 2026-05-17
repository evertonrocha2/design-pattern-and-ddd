package br.com.logistica.sharedkernel.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Placa {

    private static final Pattern PADRAO_ANTIGO = Pattern.compile("^[A-Z]{3}\\d{4}$");
    private static final Pattern PADRAO_MERCOSUL = Pattern.compile("^[A-Z]{3}\\d[A-Z]\\d{2}$");

    private final String valor;

    private Placa(String valor) {
        this.valor = valor;
    }

    public static Placa criar(String entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("Placa nao pode ser nula");
        }
        String placa = entrada.replaceAll("[-\\s]", "").toUpperCase();
        if (!PADRAO_ANTIGO.matcher(placa).matches() && !PADRAO_MERCOSUL.matcher(placa).matches()) {
            throw new IllegalArgumentException("Placa invalida");
        }
        return new Placa(placa);
    }

    public String getCodigo() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Placa placa)) return false;
        return Objects.equals(valor, placa.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
