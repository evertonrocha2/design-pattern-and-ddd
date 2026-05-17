package br.com.logistica.contexts.rastreamento.domain.valueobjects;

public final class Coordenada {

    private final double latitude;
    private final double longitude;

    private Coordenada(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Coordenada criar(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude fora do intervalo permitido");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude fora do intervalo permitido");
        }
        return new Coordenada(latitude, longitude);
    }

    public double getLatitude()  { return latitude; }
    public double getLongitude() { return longitude; }
}
