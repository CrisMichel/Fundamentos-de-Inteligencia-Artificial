package agentes;

public class NodoAEstrella {
    private int[] coordenadas; // Coordenadas del nodo
    private int costo; // Costo acumulado desde el nodo inicial hasta este nodo
    private int heuristica; // Valor heurístico (distancia estimada a la meta)

    public NodoAEstrella(int[] coordenadas, int costo, int heuristica) {
        this.coordenadas = coordenadas;
        this.costo = costo;
        this.heuristica = heuristica;
    }

    public int[] getCoordenadas() {
        return coordenadas;
    }

    public int getCosto() {
        return costo;
    }

    public int getHeuristica() {
        return heuristica;
    }
}