
package agentes;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author macario
 */
/*
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
    
    private static int contador = 0;
    private static int contadorSamples = 0;
    
    private int objetoAnterior;
    private int gradiente;
    
    private boolean sensorNorte;
    private boolean sensorSur;
    private boolean sensorEste;
    private boolean sensorOeste;
    
    private int[] coordenadasMothership;
    
    private static ImageIcon motherIcon = Escenario.getMotherIcon();
    
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

        int dirRow = 1;
        int dirCol = 1;
        
        actualizaCoordenadasMother();
        System.out.println("Coordenadas de la motherhip: " + coordenadasMothership[0]+coordenadasMothership[1]);


        while (true) {
            
            casillaAnterior = tablero[i][j];
            objetoAnterior = matrix[i][j];
            
            movimiento();

            actualizarPosicion();

            relantizar(5);
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
    public void cambiaDireccion(){
        Random random = new Random();
        
        int num1 = 0, num2 = 0, num3 = 0, num4 = 0;
        
        if(sensorNorte == true) num1 = 1;
        if(sensorSur == true) num2 = 2;
        if(sensorEste == true) num3 = 3;
        if(sensorOeste == true) num4 = 4;

        int opcMovimiento = random.nextInt(4) + 1;
        
        while(opcMovimiento == num1 || opcMovimiento == num2 || opcMovimiento == num3 || opcMovimiento == num4){
            System.out.println("Cambiando numero "+opcMovimiento);
            opcMovimiento = random.nextInt(4) + 1;
        }
        System.out.println("Se logro obtener "+opcMovimiento);
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
    //-------------------Direcciones de movimiento--------------------------
    public void movimientoNorte(){
        if (limiteNorte()) return;
        if (obstaculoNorte()) cambiaDireccion();
        i = i - 1;
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoSur(){
        if (limiteSur()) return;
        if (obstaculoSur())  cambiaDireccion();
        i = i + 1;
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoEste(){
        if (limiteEste()) return;
        if (obstaculoEste()) cambiaDireccion();
        j = j + 1;
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoOeste(){
        if (limiteOeste()) return;
        if (obstaculoOeste()) cambiaDireccion();
        j = j - 1;
        detectaSamples();
        detectaMothership();
    }
    //------------------------Deteccion de limites-----------------------------
    public boolean limiteNorte(){
        if (i <= 0) return true;
        
        return false;
    }
    
    public boolean limiteSur(){
        int limite = matrix.length;
        if (i >= limite-1) return true;
        
        return false;
    }
    
    public boolean limiteEste(){
        int limite = matrix.length;
        if (j >= limite-1) return true;
        
        return false;
     }
     
    public boolean limiteOeste(){
        if (j <= 0) return true;
        
        return false;
    }
     
     //----------------Deteccion de obstaculos--------------------------
    public boolean obstaculoNorte(){
         if (matrix[i-1][j] == 1) {
             sensorNorte = true;
             return true;
         }
         return false;
    }
     
    public boolean obstaculoSur(){
         if (matrix[i+1][j] == 1) {
             sensorSur = true;
             return true;
         } 
         return false;
    }
     
    public boolean obstaculoEste(){
         if (matrix[i][j+1] == 1) return true;
         return false;
    }
     
    public boolean obstaculoOeste(){
         if (matrix[i][j-1] == 1) return true;
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
        if (i < coordenadasMothership[0]) {
            movimientoSur(); // Ir hacia abajo
            System.out.println("Moviendose a"+ i + " " + j);
        }
        else if (i > coordenadasMothership[0]) {
            movimientoNorte(); // Ir hacia arriba
            System.out.println("Moviendose a"+ i + " " + j);
        }
        else if (j < coordenadasMothership[1]) {
            movimientoEste(); // Ir hacia la derecha
            System.out.println("Moviendose a"+ i + " " + j);
        } else if (j > coordenadasMothership[1]) {
            movimientoOeste(); // Ir hacia la izquierda
            System.out.println("Moviendose a"+ i + " " + j);
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
    
}
*/