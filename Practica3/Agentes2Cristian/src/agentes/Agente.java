
package agentes;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author macario
 */
public class Agente extends Thread
{
    private final String nombre;
    private int i;
    private int j;
    private final ImageIcon icon;
    private final int[][] matrix;
    private final JLabel tablero[][];
    
    private JLabel casillaAnterior;
    private int objetoAnterior;
    
    Random aleatorio = new Random();
    
    private int contador = 0;
    private static int contadorSamples = 0;
    
    
    private int gradiente = 0;
//    private static ArrayList<int[]> coordenadasMotherShip = new ArrayList<>();
    private int[] coordenadasMothership;
    
    private ImageIcon motherIcon = Escenario.getMotherIcon();
    private ImageIcon cerebro3Icon = Escenario.getCerebro3Icon();
    private ImageIcon cerebro2Icon = Escenario.getCerebro2Icon();
    private ImageIcon cerebro1Icon = Escenario.getCerebro1Icon();
    private ImageIcon huella1Icon = Escenario.getHuella1Icon();
    private ImageIcon huella2Icon = Escenario.getHuella2Icon();
    
    private boolean pausado = false;
    private boolean movimiento1 = false;
    
    private boolean sensorNorte = false;
    private boolean sensorSur = false;
    private boolean sensorEste = false;
    private boolean sensorOeste = false;
    
    int[] destino;
    
    public Agente(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][], int[] coordenadasMothership)
    {
        this.nombre = nombre;
        this.icon = icon;
        this.matrix = matrix;
        this.tablero = tablero;
        
        this.i = aleatorio.nextInt(matrix.length);
        this.j = aleatorio.nextInt(matrix.length);
        tablero[i][j].setIcon(icon);
        
        this.coordenadasMothership = coordenadasMothership;
    }
    
    @Override
    public void run() {
        
        actualizaCoordenadasMother();
        System.out.println("Coordenadas de la motherhip: " + coordenadasMothership[0]+coordenadasMothership[1]);

        while (true) {
            
            if (pausado) synchronized (this) {
                    try {wait();}
                    catch (InterruptedException e) {}
            }
            
            casillaAnterior = tablero[i][j];
            objetoAnterior = matrix[i][j];
            
            movimiento();
            actualizarPosicion();
            relantizar(3);
        }

    }
    
    public synchronized void actualizarPosicion()
    {
        actualizarPosicionAnterior();
        tablero[i][j].setIcon(icon);    // Pone su figura en la nueva casilla
        
//        System.out.println(nombre + " in -> Row: " + i + " Col:"+ j);              
    }
    
    public synchronized void actualizarPosicionAnterior(){
        
        if(objetoAnterior == 3) casillaAnterior.setIcon(cerebro3Icon);      // Cambia el icono a 3 cerebros
        else if(objetoAnterior == 2) casillaAnterior.setIcon(cerebro2Icon); // Cambia el icono a 2 cerebros
        else if(objetoAnterior == 1) casillaAnterior.setIcon(cerebro1Icon); // Cambia el icono a 1 cerebros
        else if(objetoAnterior == -6) casillaAnterior.setIcon(motherIcon);  // Reestablece la imagen de la mothership
        else if(objetoAnterior == -2) casillaAnterior.setIcon(huella2Icon);
        else if(objetoAnterior == -1) casillaAnterior.setIcon(huella1Icon);
        else casillaAnterior.setIcon(null);                                 // Elimina su figura de la casilla anterior;
    }
    /*
    --------------------------------------------------------------------------------------------
    --------------------FUNCIONES EXCLUSIVAS PARA EL MOVIMIENTO DEL AGENTE----------------------
    --------------------------------------------------------------------------------------------
    */
    public void movimiento(){
        if (contador > 0) movimientoHaciaMothershipAStar();
        
        else {
            movimientoRandom();
//            System.out.println("Ultimo movimiento random");
        }
        
    }
    //----------------------------MOVIMIENTO HACIA LA MOTHERSHIP--------------------------------
    private void movimientoHaciaMothership() {
        if (j < coordenadasMothership[1]) movimientoSur(); // Ir hacia abajo
        else if (i < coordenadasMothership[0]) movimientoEste(); // Ir hacia la derecha
        else if (j > coordenadasMothership[1]) movimientoNorte(); // Ir hacia arriba
        else if (i > coordenadasMothership[0]) movimientoOeste(); // Ir hacia la izquierda
    }
    //-------------------------------MOVIMIENTO RANDOM------------------------------
    public void movimientoRandom(){
        
        Random random = new Random();

        int opcMovimiento = random.nextInt(4) + 1;
        
        detectaSamplesCercanas();
        
        if(sensorNorte) opcMovimiento = 1;
        else if(sensorSur) opcMovimiento = 2;
        else if(sensorEste) opcMovimiento = 3;
        else if(sensorOeste) opcMovimiento = 4;
        
        switch (opcMovimiento){
            case 1: //Movimiento al norte
                movimientoNorte();
                break;
            case 2: //Moviendose al sur
                movimientoSur();
                break;
            case 3: //Movimiento al este
                movimientoEste();
                break;
            case 4: //Moviendose al oeste
                movimientoOeste();
                break;
            default:
                break;
        }
    }
    
    public void movimientoSiguiendoMigas(){
        boolean este = false, sur = false, oeste = false, norte = false;
        int limites = matrix.length;
        
        int opcion1, opcion2;
        
        if(i < limites-1 && matrix[i+1][j] < 0 && matrix[i+1][j] > -5) {
            este = true;
        }
        else if(j < limites-1 && matrix[i][j+1] < 0 && matrix[i][j+1] > -5) {
            sur = true;
        }
        else if(i < 0 && matrix[i-1][j] < 0 && matrix[i-1][j] > -5) {
            oeste = true;
        }
        else if(j < 0 && matrix[i][j-1] < 0 && matrix[i][j-1] > -5) {
            norte = true;
        }
        
    }
    
    public void cambiaDireccion(int direccionActual) {
        ArrayList<Integer> direccionesValidas = new ArrayList<>();

        // Comprobar direcciones válidas
        if (!limiteNorte() && !obstaculoNorte() && direccionActual != 1) {
            direccionesValidas.add(1); // Norte
        }
        if (!limiteSur() && !obstaculoSur() && direccionActual != 2) {
            direccionesValidas.add(2); // Sur
        }
        if (!limiteEste() && !obstaculoEste() && direccionActual != 3) {
            direccionesValidas.add(3); // Este
        }
        if (!limiteOeste() && !obstaculoOeste() && direccionActual != 4) {
            direccionesValidas.add(4); // Oeste
        }

        // Elegir una dirección aleatoria entre las válidas
        if (!direccionesValidas.isEmpty()) {
            Random random = new Random();
            int indiceAleatorio = random.nextInt(direccionesValidas.size());
            int nuevaDireccion = direccionesValidas.get(indiceAleatorio);

            // Mover en la nueva dirección
            switch (nuevaDireccion) {
                case 1:
                    movimientoNorte();
                    break;
                case 2:
                    movimientoSur();
                    break;
                case 3:
                    movimientoEste();
                    break;
                case 4:
                    movimientoOeste();
                    break;
            }
        }
    }

    //-------------------Direcciones de movimiento--------------------------
    public void movimientoNorte(){
        if (limiteNorte() || obstaculoNorte()) {
            cambiaDireccion(1);
            return;
        }
        j = j - 1;
        detectaObjetos();
    }
    
    public void movimientoSur(){
        if (limiteSur() || obstaculoSur())  {
            cambiaDireccion(2);
            return;
        }
        j = j + 1;
        detectaObjetos();
    }
    
    public void movimientoEste(){
        if (limiteEste() || obstaculoEste()) {
            cambiaDireccion(3);
            return;
        }
        i = i + 1;
        detectaObjetos();
    }
    
    public void movimientoOeste(){
        if (limiteOeste() || obstaculoOeste()) {
            cambiaDireccion(4);
            return;
        }
        i = i - 1;
        detectaObjetos();
    }
    //------------------------Deteccion de objetos-----------------------------
    public void detectaObjetos() {
        detectaMigas();
        detectaSamples();
        detectaMothership();
        dejaMigas();
    }
    //------------------------Deteccion de limites-----------------------------
    public boolean limiteNorte(){
        if (j <= 0) return true;
        
        return false;
    }
    
    public boolean limiteSur(){
        int limite = matrix.length;
        if (j >= limite-1) return true;
        
        return false;
    }
    
    public boolean limiteEste(){
        int limite = matrix.length;
        if (i >= limite-1) return true;
        
        return false;
     }
     
    public boolean limiteOeste(){
        if (i <= 0) return true;
        
        return false;
    }
     
     //----------------Deteccion de obstaculos--------------------------
    public boolean obstaculoNorte(){
         if (matrix[i][j-1] == -5) return true;
         return false;
    }
     
    public boolean obstaculoSur(){
         if (matrix[i][j+1] == -5) return true;
         return false;
    }
     
    public boolean obstaculoEste(){
        if (matrix[i+1][j] == -5) return true;
        return false;
    }
     
    public boolean obstaculoOeste(){
        if (matrix[i-1][j] == -5) return true;
        return false;
    }
    //-----------Deteccion de samples-------------
    public void detectaSamples(){
        if (matrix[i][j] > 0){
            contador++;
            matrix[i][j]--;
        }
    }
    //-----------Deteccion de mothership-------------
    public void detectaMothership(){
        if (matrix[i][j] == -6){
            contadorSamples += contador;
            if (contador > 0) Escenario.getMuestrasDepositadasLabel().setText(String.valueOf(contadorSamples));
            contador = 0;
        }
    }
    //-----------Deteccion de samples cercanas--------
    public void detectaSamplesCercanas(){
        sensorNorte = false;
        sensorSur = false;
        sensorEste = false;
        sensorOeste = false;
        
        if (i > 0 && matrix[i - 1][j] > 0) sensorOeste = true;
        else if (i < matrix.length - 1 && matrix[i + 1][j] > 0) sensorEste = true;
        else if (j > 0 && matrix[i][j - 1] > 0) sensorNorte = true;
        else if (j < matrix.length - 1 && matrix[i][j + 1] > 0) sensorSur = true;
    }
    //-----------Deteccion de migas-------------
    public void detectaMigas(){
        if (contador == 0 && matrix[i][j] < 0 && matrix[i][j] > -5){
            matrix[i][j]++; // Si detecta 2 migas, recogera 1
        }
    }
    //-----------Colocación de migas-------------
    public void dejaMigas(){
        if(contador > 0 && matrix[i][j] == 0){
            matrix[i][j] = -2; // Cuando posea una muestra, dejara un numero -2 en cada casilla. El numero -2 representa 2 migajas
        }
    }
    
    public void actualizaCoordenadasMother(){
        for(int x=0; x<matrix.length; x++)
            for(int y=0; y<matrix.length; y++)
                if (matrix[x][y] == -6){
                    coordenadasMothership = new int[]{x, y};
                }
    }
    
    public int calculaManhattan(int i, int j){
        int num1 = i - coordenadasMothership[0];
        int num2 = j - coordenadasMothership[1];      
        
        if (num1<0) num1*=-1;
        if (num2<0) num2*=-1;
        
        return num1 + num2;
        
    }
    
    public void relantizar(int num){
        try {
            sleep(num*100);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public synchronized void pausar() {
        pausado = true;
    }
    
    public synchronized void reanudar() {
        pausado = false;
        notify();
        System.out.println("Hilo reanudado");
    }
    
    public synchronized void unMovimiento() {
        casillaAnterior = tablero[i][j];
        objetoAnterior = matrix[i][j];
        if (contador>0){
             casillaAnterior = tablero[i][j];
            objetoAnterior = matrix[i][j];

            moverHaciaDestino(destino);
            actualizarPosicion();
            relantizar(3);
        }
        else movimiento();

        actualizarPosicion();
    }
    
    public static int getContadorSamples(){
        return contadorSamples;
    }
    
    public static void setContadorSamples(int num){
        contadorSamples = num;
    }
    
    //--------------------------------------------------------------------------
    //                      MOVIMIENTO BUSQUEDA A*
    //--------------------------------------------------------------------------
    public void movimientoHaciaMothershipAStar() {
        destino = coordenadasMothership;
        PriorityQueue<NodoAEstrella> colaAbierta = new PriorityQueue<>((n1, n2) ->
                Integer.compare(n1.getCosto() + n1.getHeuristica(), n2.getCosto() + n2.getHeuristica()));

        NodoAEstrella nodoInicial = new NodoAEstrella(new int[]{i, j}, 0, calcularHeuristica(new int[]{i, j}, destino));
        colaAbierta.offer(nodoInicial);

        while (!colaAbierta.isEmpty()) {
            NodoAEstrella nodoActual = colaAbierta.poll();
            int[] coordenadasActuales = nodoActual.getCoordenadas();

            if (esDestino(coordenadasActuales, destino)) {
                // Llegamos al destino, realizar movimientos para llegar al nodo de destino
                while (!esDestino(new int[]{i, j}, destino)) {
                    
                    if (pausado) synchronized (this) {
                        try {wait();}
                        catch (InterruptedException e) {}
                    }
                    
                    casillaAnterior = tablero[i][j];
                    objetoAnterior = matrix[i][j];

                    moverHaciaDestino(destino);
                    actualizarPosicion();
                    relantizar(3);
                }
                break;
            }

            // Generar y agregar nodos sucesores a la cola de prioridad
            generarSucesores(nodoActual, colaAbierta, destino);
        }
    }
    private void generarSucesores(NodoAEstrella nodoPadre, PriorityQueue<NodoAEstrella> colaAbierta, int[] destino) {
        int[] coordenadasPadre = nodoPadre.getCoordenadas();
        int costoPadre = nodoPadre.getCosto();

        // Generar nodos sucesores (movimientos posibles: norte, sur, este, oeste)
        int[][] movimientos = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        for (int[] movimiento : movimientos) {
            int nuevaI = coordenadasPadre[0] + movimiento[0];
            int nuevaJ = coordenadasPadre[1] + movimiento[1];

            // Verificar si el movimiento es válido (dentro del tablero y no es obstáculo)
            if (esMovimientoValido(nuevaI, nuevaJ) && !esMovimientoBloqueado(nuevaI, nuevaJ, destino)) {
                int costoMovimiento = costoPadre + 1; // Costo uniforme, ya que todos los movimientos tienen el mismo costo
                int heuristica = calcularHeuristica(new int[]{nuevaI, nuevaJ}, destino);
                NodoAEstrella sucesor = new NodoAEstrella(new int[]{nuevaI, nuevaJ}, costoMovimiento, heuristica);

                colaAbierta.offer(sucesor);
            }
        }
    }
    private boolean esMovimientoBloqueado(int nuevaI, int nuevaJ, int[] destino) {
        // Verificar si el movimiento conduce a un bloqueo (obstáculo a la derecha de la nave nodriza)
        return nuevaI < destino[0] && nuevaJ > destino[1] && matrix[destino[0]][destino[1] + 1] == -5;
    }
    private boolean esMovimientoValido(int nuevaI, int nuevaJ) {
        return nuevaI >= 0 && nuevaI < matrix.length && nuevaJ >= 0 && nuevaJ < matrix.length &&
               matrix[nuevaI][nuevaJ] != -5; // No es obstáculo
    }
    private boolean esDestino(int[] coordenadas, int[] destino) {
        return coordenadas[0] == destino[0] && coordenadas[1] == destino[1];
    }
    private void moverHaciaDestino(int[] destino) {
        if (i < destino[0]) {
            movimientoEste();
        } else if (i > destino[0]) {
            movimientoOeste();
        } else if (j < destino[1]) {
            movimientoSur();
        } else if (j > destino[1]) {
            movimientoNorte();
        }
        
    }
    private int calcularHeuristica(int[] origen, int[] destino) {
        // Heurística: distancia Manhattan entre el nodo actual y el destino
        return Math.abs(origen[0] - destino[0]) + Math.abs(origen[1] - destino[1]);
    }
}