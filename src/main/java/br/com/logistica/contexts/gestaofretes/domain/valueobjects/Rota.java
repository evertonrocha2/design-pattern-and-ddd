package br.com.logistica.contexts.gestaofretes.domain.valueobjects;

import br.com.logistica.sharedkernel.domain.valueobjects.Endereco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Rota {

    private final Endereco origem;
    private final Endereco destino;
    private final double distanciaEmKm;
    private final List<Endereco> paradas;

    private Rota(Endereco origem, Endereco destino, double distanciaEmKm, List<Endereco> paradas) {
        this.origem = origem;
        this.destino = destino;
        this.distanciaEmKm = distanciaEmKm;
        this.paradas = paradas;
    }

    public static Rota calcular(Endereco origem, Endereco destino, double distanciaEmKm) {
        return calcular(origem, destino, distanciaEmKm, new ArrayList<>());
    }

    public static Rota calcular(Endereco origem, Endereco destino,
                                 double distanciaEmKm, List<Endereco> paradas) {
        if (distanciaEmKm <= 0) {
            throw new IllegalArgumentException("Distancia da rota deve ser positiva");
        }
        return new Rota(origem, destino, distanciaEmKm,
                Collections.unmodifiableList(new ArrayList<>(paradas)));
    }

    public Endereco getOrigem()      { return origem; }
    public Endereco getDestino()     { return destino; }
    public double getDistanciaEmKm() { return distanciaEmKm; }
    public List<Endereco> getParadas() { return paradas; }
}
