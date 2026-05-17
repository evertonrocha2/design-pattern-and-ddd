package br.com.logistica.sharedkernel.domain.identifiers;

import java.util.Objects;
import java.util.UUID;

public final class PedidoDeFreteId {

    private final String valor;

    private PedidoDeFreteId(String valor) {
        this.valor = valor;
    }

    public static PedidoDeFreteId novo() {
        return new PedidoDeFreteId(UUID.randomUUID().toString());
    }

    public static PedidoDeFreteId criar(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Identificador de pedido de frete nao pode ser vazio");
        }
        return new PedidoDeFreteId(valor);
    }

    public String getCodigo() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoDeFreteId that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
