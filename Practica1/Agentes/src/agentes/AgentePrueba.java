
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
public class AgentePrueba extends Thread
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
//    private static ArrayList<int[]> coordenadasMotherShip = new ArrayList<>();
    private int[] coordenadasMothership;
    
    private static ImageIcon motherIcon = Escenario.getMotherIcon();
    
    public AgentePrueba(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][], int[] coordenadasMothership)
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

//            try {
//                sleep(100 + aleatorio.nextInt(100));
//            } catch (InterruptedException ex) {
//                ex.printStackTrace(System.out);
//            }
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
        System.out.println("Cordenada actual "+ i+ " "+ j);
//        if (contador > 0) movimientoDefinido();
//        else 
           movimientoRandom();
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
    public void cambiaDireccion(int num){
        Random random = new Random();
        
        System.out.println("Cambiando direccion");

        int opcMovimiento = random.nextInt(4) + 1;
        
        while(opcMovimiento == num){
            System.out.println("Numero "+num);
            opcMovimiento = random.nextInt(4) + 1;
        }
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
        if (limiteNorte()) cambiaDireccion(1);
        if (obstaculoNorte()) cambiaDireccion(1);
        System.out.println("Moviendose a "+ (i-1) + " " + j);
        j = j - 1;
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoSur(){
        if (limiteSur())  cambiaDireccion(2);
        if (obstaculoSur()) cambiaDireccion(2);
        System.out.println("Moviendose a "+ (i+1) + " " + j);
        j = j + 1;
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoEste(){
        if (limiteEste()) cambiaDireccion(3);
        if (obstaculoEste()) cambiaDireccion(3);
        System.out.println("Moviendose a "+ (j+1) + " " + j);
        i = i + 1;
        detectaSamples();
        detectaMothership();
    }
    
    public void movimientoOeste(){
        if (limiteOeste()) cambiaDireccion(4);
        if (obstaculoOeste()) cambiaDireccion(4);
        System.out.println("Moviendose a "+ (j-1) + " " + j);
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
        System.out.println("Coordenada actual " +i +" "+j);
        if (matrix[i][j-1] == 1) return true;
         return false;
    }
     
    public boolean obstaculoSur(){
        System.out.println("Coordenada actual " +i +" "+j);
        if (matrix[i][j+1] == 1) return true;
         return false;
    }
     
    public boolean obstaculoEste(){
        System.out.println("Coordenada actual " +i +" "+j);
         if (matrix[i+1][j] == 1) return true;
         return false;
    }
     
    public boolean obstaculoOeste(){
        System.out.println("Coordenada actual " +i +" "+j);
        if (matrix[i-1][j] == 1) return true;
        return false;
    }
    //-----------Deteccion de samples-------------
    public void detectaSamples(){
        if (matrix[i][j] == 2){
            contador++;
//            System.out.println("\nCantidad de samples recogidos " + contador + "\n");
            matrix[i][j] = 0;
        }
    }
    //-----------Deteccion de mothership-------------
    public void detectaMothership(){
        if (matrix[i][j] == 3){
            contadorSamples += contador;
//            System.out.println("Cantidad de samples depositados " + contador);
            contador = 0;
//            System.out.println("Cantidad de samples totales " + contadorSamples);
        }
    }
    //---------------------MOVIMIENTO DEFINIDO HACIA LA MOTHERSHIP------------------------------
    public void movimientoDefinido(){
            irAMotherShip();
    }

    private void irAMotherShip() {
        if (i < coordenadasMothership[0]) {
            System.out.println("Moviendose a "+ (i+1) + " " + j);
            movimientoSur(); // Ir hacia abajo
        }
        else if (i > coordenadasMothership[0]) {
            System.out.println("Moviendose a "+ (i+1) + " " + j);
            movimientoNorte(); // Ir hacia arriba
        }
        else if (j < coordenadasMothership[1]) {
            System.out.println("Moviendose a "+ (i+1) + " " + j);
            movimientoEste(); // Ir hacia la derecha
        } else if (j > coordenadasMothership[1]) {
            System.out.println("Moviendose a "+ (i+1) + " " + j);
            movimientoOeste(); // Ir hacia la izquierda
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
