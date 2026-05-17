package br.com.logistica.sharedkernel.domain.valueobjects;

import java.util.Objects;

public final class Endereco {

    private final String logradouro;
    private final String numero;
    private final String bairro;
    private final String cidade;
    private final String estado;
    private final String cep;

    private Endereco(String logradouro, String numero, String bairro,
                     String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public static Endereco criar(String logradouro, String numero, String bairro,
                                 String cidade, String estado, String cep) {
        if (cep == null || !cep.matches("\\d{5}-?\\d{3}")) {
            throw new IllegalArgumentException("CEP invalido");
        }
        if (estado == null || estado.length() != 2) {
            throw new IllegalArgumentException("Estado deve ter 2 letras");
        }
        return new Endereco(logradouro, numero, bairro, cidade, estado, cep.replace("-", ""));
    }

    public String getLogradouro() { return logradouro; }
    public String getNumero() { return numero; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getCep() { return cep; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco that)) return false;
        return Objects.equals(cep, that.cep)
                && Objects.equals(numero, that.numero)
                && Objects.equals(logradouro, that.logradouro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, cep);
    }
}
