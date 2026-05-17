package br.com.logistica.sharedkernel.domain.valueobjects;

import java.util.Objects;

public final class Cnpj {

    private final String numero;

    private Cnpj(String numero) {
        this.numero = numero;
    }

    public static Cnpj criar(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("CNPJ nao pode ser nulo");
        }
        String numero = valor.replaceAll("\\D", "");
        if (numero.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve ter 14 digitos");
        }
        if (!validarDigitos(numero)) {
            throw new IllegalArgumentException("CNPJ invalido");
        }
        return new Cnpj(numero);
    }

    public String getValor() { return numero; }

    public String formatado() {
        return String.format("%s.%s.%s/%s-%s",
                numero.substring(0, 2),
                numero.substring(2, 5),
                numero.substring(5, 8),
                numero.substring(8, 12),
                numero.substring(12, 14));
    }

    private static boolean validarDigitos(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int digito1 = calcular(cnpj.substring(0, 12), pesos1);
        int digito2 = calcular(cnpj.substring(0, 12) + digito1, pesos2);

        return cnpj.endsWith("" + digito1 + digito2);
    }

    private static int calcular(String base, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            soma += Character.getNumericValue(base.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cnpj cnpj)) return false;
        return Objects.equals(numero, cnpj.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
