
package agentes;

import java.util.ArrayList;
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
    Random aleatorio = new Random(System.currentTimeMillis());
    
    private int contador = 0;
    private static int contadorSamples = 0;
    
    private int objetoAnterior;
    private int gradiente;
//    private static ArrayList<int[]> coordenadasMotherShip = new ArrayList<>();
    private int[] coordenadasMothership;
    
    private static ImageIcon motherIcon = Escenario.getMotherIcon();
    
    private boolean pausado = false;
    
    private boolean sensorNorte;
    private boolean sensorSur;
    private boolean sensorEste;
    private boolean sensorOeste;
    
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
                    try {
                        wait();
                    } catch (InterruptedException e) {}
                }
            
            casillaAnterior = tablero[i][j];
            objetoAnterior = matrix[i][j];
            
            movimiento();

            actualizarPosicion();

            relantizar(1);
        }

    }
    
    public synchronized void actualizarPosicion()
    {
        if(objetoAnterior == 3) casillaAnterior.setIcon(motherIcon);// Reestablece la imagen de la mothership
        
        else casillaAnterior.setIcon(null);                         // Elimina su figura de la casilla anterior
        
        tablero[i][j].setIcon(icon);                                // Pone su figura en la nueva casilla
        
//        System.out.println(nombre + " in -> Row: " + i + " Col:"+ j);              
    }
    /*
    --------------------------------------------------------------------------------------------
    --------------------FUNCIONES EXCLUSIVAS PARA EL MOVIMIENTO DEL AGENTE----------------------
    --------------------------------------------------------------------------------------------
    */
    public void movimiento(){
        if (contador > 0) movimientoDefinido();
        else movimientoRandom();
    }
    //-------------------------------MOVIMIENTO RANDOM------------------------------
    public void movimientoRandom(){
        Random random = new Random();

        int opcMovimiento = random.nextInt(4) + 1;
        
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
    
//    public void cambiaDireccion(int num){// En num se coloca la direccion que se quiere 
//        Random random = new Random();
//
//        int opcMovimiento = random.nextInt(4) + 1;
//        
//        while(opcMovimiento == num){
//            opcMovimiento = random.nextInt(4) + 1;
//        }
//        switch (opcMovimiento){
//            case 1: //Movimiento al norte
//                movimientoNorte();
//                break;
//            case 2: //Moviendose al sur
//                movimientoSur();
//                break;
//            case 3: //Movimiento al este
//                movimientoEste();
//                break;
//            case 4: //Moviendose al oeste
//                movimientoOeste();
//                break;
//            default:
//                break;
//        }
//    }

    //-------------------Direcciones de movimiento--------------------------
    public void movimientoNorte(){
        if (limiteNorte() || obstaculoNorte()) {
            cambiaDireccion(1);
            return;
        }
        j = j - 1;
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoSur(){
        if (limiteSur() || obstaculoSur())  {
            cambiaDireccion(2);
            return;
        }
        j = j + 1;
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoEste(){
        if (limiteEste() || obstaculoEste()) {
            cambiaDireccion(3);
            return;
        }
        i = i + 1;
        
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoOeste(){
        if (limiteOeste() || obstaculoOeste()) {
            cambiaDireccion(4);
            return;
        }
        i = i - 1;
        
        detectaSamples();
        detectaMothership();
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
         if (matrix[i][j-1] == 1) return true;
         return false;
    }
     
    public boolean obstaculoSur(){
         if (matrix[i][j+1] == 1) return true;
         return false;
    }
     
    public boolean obstaculoEste(){
        if (matrix[i+1][j] == 1) return true;
        return false;
    }
     
    public boolean obstaculoOeste(){
        if (matrix[i-1][j] == 1) return true;
        return false;
    }
    //-----------Deteccion de samples-------------
    public void detectaSamples(){
        if (matrix[i][j] == 2){
            contador++;
            System.out.println("\nCantidad de samples recogidos " + contador + "\n");
            matrix[i][j] = 0;
        }
    }
    //-----------Deteccion de mothership-------------
    public void detectaMothership(){
        if (matrix[i][j] == 3){
            contadorSamples += contador;
            System.out.println("Cantidad de samples depositados " + contador);
            contador = 0;
            System.out.println("Cantidad de samples totales " + contadorSamples);
        }
    }
    //---------------------MOVIMIENTO DEFINIDO HACIA LA MOTHERSHIP------------------------------
    public void movimientoDefinido(){
            irAMotherShip();
    }

    private void irAMotherShip() {
        if (j < coordenadasMothership[1]) {
            movimientoSur(); // Ir hacia abajo
            System.out.println("Moviendose a "+ i + " " + j);
        }
        else if (j > coordenadasMothership[1]) {
            movimientoNorte(); // Ir hacia arriba
            System.out.println("Moviendose a "+ i + " " + j);
        }
        else if (i < coordenadasMothership[0]) {
            movimientoEste(); // Ir hacia la derecha
            System.out.println("Moviendose a "+ i + " " + j);
        } else if (i > coordenadasMothership[0]) {
            movimientoOeste(); // Ir hacia la izquierda
            System.out.println("Moviendose a "+ i + " " + j);
        }
    }
    
    public void actualizaCoordenadasMother(){
        for(int x=0; x<matrix.length; x++)
            for(int y=0; y<matrix.length; y++)
                if (matrix[x][y]==3){
                    coordenadasMothership = new int[]{x, y};
                }
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
    
}