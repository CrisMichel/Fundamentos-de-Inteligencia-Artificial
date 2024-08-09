
package agentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author macario
 */
public class Escenario extends JFrame
{
    
    private JLabel[][] tablero;     
    private int[][] matrix;
    private final int dim = 12;

    private ImageIcon robot1;
    private ImageIcon robot2;
    private ImageIcon obstacleIcon;
    private ImageIcon sampleIcon;
    private ImageIcon actualIcon;
    private static ImageIcon motherIcon;
    
    private Agente wallE;
    private Agente eva;
   
    private final BackGroundPanel fondo = new BackGroundPanel(new ImageIcon("imagenes/pasto2.png"));
    
    private final JMenu settings = new JMenu("Settigs");
    private final JRadioButtonMenuItem obstacle = new JRadioButtonMenuItem("Obstacle");
    private final JRadioButtonMenuItem sample = new JRadioButtonMenuItem("Sample");
    private final JRadioButtonMenuItem motherShip = new JRadioButtonMenuItem("MotherShip");
    private final JRadioButtonMenuItem randomSettings = new JRadioButtonMenuItem("Configuracion Aleatoria");
    
    private int[] coordenadasMothership = {0, 0};
    
    private JLabel obstaculosLabel;
    private JLabel muestrasLabel;
    
    private int cantidadObstaculos = 0, cantidadMuestras = 0;
    
    private boolean enPausa = false;
    JButton  runButton;
    JButton randomButton;
    
    public Escenario()
    {
        this.setContentPane(fondo);
        this.setTitle("Agentes");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setBounds(50,50,dim*50+200,dim*50+85);
        initComponents();
    }
        
    private void initComponents()
    {
        ButtonGroup settingsOptions = new ButtonGroup();
        settingsOptions.add(sample);
        settingsOptions.add(obstacle);       
        settingsOptions.add(motherShip);
        settingsOptions.add(randomSettings);
        
        JMenuBar barraMenus = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem run  = new JMenuItem("Run");
        
        JMenuItem exit   = new JMenuItem("Exit");
              
        this.setJMenuBar(barraMenus);
        barraMenus.add(file);
        barraMenus.add(settings);
        file.add(run);
        file.add(exit);
        settings.add(motherShip);
        settings.add(obstacle);
        settings.add(sample);
        settings.add(randomSettings);
            
        robot1 = new ImageIcon("imagenes/zombi.png");
        robot1 = new ImageIcon(robot1.getImage().getScaledInstance(55,55,  java.awt.Image.SCALE_SMOOTH));
        
        robot2 = new ImageIcon("imagenes/zombi2.png");
        robot2 = new ImageIcon(robot2.getImage().getScaledInstance(50,55,  java.awt.Image.SCALE_SMOOTH));
        
        obstacleIcon = new ImageIcon("imagenes/nut.png");
        obstacleIcon = new ImageIcon(obstacleIcon.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        
        sampleIcon = new ImageIcon("imagenes/cerebro.png");
        sampleIcon = new ImageIcon(sampleIcon.getImage().getScaledInstance(30,30,  java.awt.Image.SCALE_SMOOTH));
        
        motherIcon = new ImageIcon("imagenes/zombot3.png");
        motherIcon = new ImageIcon(motherIcon.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        
        this.setLayout(null);
        formaPlano();  
        
        exit.addActionListener(evt -> gestionaSalir(evt));
        run.addActionListener(evt -> gestionaRun(evt));
        obstacle.addItemListener(evt -> gestionaObstacle(evt));
        sample.addItemListener(evt -> gestionaSample(evt));
        motherShip.addItemListener(evt -> gestionaMotherShip(evt));
        randomSettings.addActionListener(evt ->gestionaRandom(evt));

            
        class MyWindowAdapter extends WindowAdapter
        {
            @Override
            public void windowClosing(WindowEvent eventObject)
            {
		goodBye();
            }
        }
        addWindowListener(new MyWindowAdapter());
        
        // Crea 2 agentes
        wallE = new Agente("Wall-E",robot1, matrix, tablero, coordenadasMothership); 
        eva = new Agente("Eva",robot2, matrix, tablero, coordenadasMothership); 
        //--------------------------------------------------------------
        //--------------------------SidePanel---------------------------
        //--------------------------------------------------------------
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBounds(dim * 50 + 20, 10, 150, dim * 50);
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sidePanel.setAlignmentX(CENTER_ALIGNMENT);

        obstaculosLabel = new JLabel("Obstáculos: 0");
        muestrasLabel = new JLabel("Muestras: 0");

        runButton = new JButton("Run");
        JButton pauseButton = new JButton("Pausar");
        randomButton = new JButton("Aleatorio");

        runButton.setMaximumSize(new Dimension(100, 30)); // Ancho máximo y alto fijo
        pauseButton.setMaximumSize(new Dimension(100, 30)); // Ancho máximo y alto fijo
        randomButton.setMaximumSize(new Dimension(100, 100)); // Ancho máximo y alto fijo
        
        obstaculosLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        muestrasLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        runButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        pauseButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        randomButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(obstaculosLabel);
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(muestrasLabel);
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(runButton);
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(pauseButton);
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(randomButton);
        sidePanel.add(Box.createVerticalGlue());
        
        pauseButton.addActionListener(evt -> {
        if (!enPausa) gestionaPause(evt);
        else gestionaReanudar(evt);
        });
        
        runButton.addActionListener(evt -> gestionaRun(evt));
//        pauseButton.addActionListener(evt -> gestionaPause(evt));
        randomButton.addActionListener(evt -> gestionaRandom(evt));

        add(sidePanel);

    }
        
    private void gestionaSalir(ActionEvent eventObject)
    {
        goodBye();
    }
        
    private void goodBye()
    {
//        int respuesta = JOptionPane.showConfirmDialog(rootPane, "Desea salir?","Aviso",JOptionPane.YES_NO_OPTION);
//        if(respuesta==JOptionPane.YES_OPTION) System.exit(0);
        System.exit(0);
    }
  
    private void formaPlano()
    {
        tablero = new JLabel[dim][dim];
        matrix = new int[dim][dim];
        
        int i, j;
        
        for(i=0;i<dim;i++)
            for(j=0;j<dim;j++)
            {
                matrix[i][j]=0;
                tablero[i][j]=new JLabel();
                tablero[i][j].setBounds(j*50+10,i*50+10,50,50);
                tablero[i][j].setBorder(BorderFactory.createDashedBorder(Color.white));
                tablero[i][j].setOpaque(false);
                this.add(tablero[i][j]);
                
                tablero[i][j].addMouseListener(new MouseAdapter() // Este listener nos ayuda a agregar poner objetos en la rejilla
                    {
                        @Override
                        public void mousePressed(MouseEvent e) 
                        {
                               insertaObjeto(e);
                        }   
                
                        @Override
                        public void mouseReleased(MouseEvent e) 
                        {
                                insertaObjeto(e);
                        }   
                
                    });
                                
            }
    }
        
    private void gestionaObstacle(ItemEvent eventObject)
    {
        JRadioButtonMenuItem opt = (JRadioButtonMenuItem) eventObject.getSource();
        if(opt.isSelected())
           actualIcon = obstacleIcon;
        else actualIcon = null;        
    }
    
    private void gestionaSample(ItemEvent eventObject)
    {
        JRadioButtonMenuItem opt = (JRadioButtonMenuItem) eventObject.getSource();
        if(opt.isSelected())
           actualIcon = sampleIcon;
        else actualIcon = null;   
    }
    
    private void gestionaMotherShip(ItemEvent eventObject)
    {
        JRadioButtonMenuItem opt = (JRadioButtonMenuItem) eventObject.getSource();
        if(opt.isSelected())
           actualIcon = motherIcon;
        else actualIcon = null;   
    }
    private void gestionaRun(ActionEvent eventObject)
    {
        if(!wallE.isAlive()) wallE.start();
        if(!eva.isAlive()) eva.start();
        settings.setEnabled(false);
        runButton.setEnabled(false);
        randomButton.setEnabled(false);
        
        enPausa = false;
        actualizaContadores();
    }
       
    public void insertaObjeto(MouseEvent e)
    {
        JLabel casilla = (JLabel) e.getSource();
        if(actualIcon!=null) casilla.setIcon(actualIcon); 
        //Colocamos en la matriz un numero especifico para cada objeto
        
        int i = (casilla.getY() - 10) / 50;
        int j = (casilla.getX() - 10) / 50;
        
        if (actualIcon != null) {
        // Verificar si ya hay un objeto en esa posición
        if (matrix[i][j] == 0) {
            casilla.setIcon(actualIcon);
            
            // Colocamos en la matriz un número específico para cada objeto
            if (actualIcon == obstacleIcon) {
                matrix[i][j] = 1; // La matriz tendrá 1 si hay un obstáculo
                cantidadObstaculos++;
            } else if (actualIcon == sampleIcon) {
                matrix[i][j] = 2; // La matriz tendrá 2 si hay una muestra
                cantidadMuestras++;
            } else if (actualIcon == motherIcon) {
                matrix[i][j] = 3; // La matriz tendrá 3 si hay una mothership
                coordenadasMothership = new int[]{i, j};
            }
            actualizaContadores();
        }
    }

    }
    
    public void gestionaRandom(ActionEvent e) {
        // Limpia el tablero
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matrix[i][j] = 0;
                tablero[i][j].setIcon(null);
            }
        }
        cantidadMuestras=0;
        cantidadObstaculos=0;
        // Coloca una mothership
        colocarMothership();
        // Coloca samples
        colocarSamples(15);
        // Coloca obstáculos
        colocarObstaculos(6);
        // Recoloca a los agentes de manera aleatoria
        colocarAgentes();
        // Habilita los controles de configuración
        settings.setEnabled(true);
        runButton.setEnabled(true);
        
        actualizaContadores();
    }

    private void colocarMothership() {
        Random random = new Random();
        int i, j;

        do {
            i = random.nextInt(dim);
            j = random.nextInt(dim);
        } while (matrix[i][j] != 0);

        matrix[i][j] = 3; // Mothership
        tablero[i][j].setIcon(motherIcon);
        coordenadasMothership = new int[]{i, j};
    }

    private void colocarSamples(int numSamples) {
        Random random = new Random();

        for (int count = 0; count < numSamples; count++) {
            int i, j;

            do {
                i = random.nextInt(dim);
                j = random.nextInt(dim);
            } while (matrix[i][j] != 0);

            matrix[i][j] = 2; // Sample
            tablero[i][j].setIcon(sampleIcon);
            cantidadMuestras++;
        }
    }

    private void colocarObstaculos(int numObstaculos) {
        Random random = new Random();

        for (int count = 0; count < numObstaculos; count++) {
            int i, j;

            do {
                i = random.nextInt(dim);
                j = random.nextInt(dim);
            } while (matrix[i][j] != 0);

            matrix[i][j] = 1; // Obstáculo
            tablero[i][j].setIcon(obstacleIcon);
            cantidadObstaculos++;
        }
    }
    
    private void colocarAgentes(){
        robot1 = new ImageIcon("imagenes/zombi.png");
        robot1 = new ImageIcon(robot1.getImage().getScaledInstance(55, 55, java.awt.Image.SCALE_SMOOTH));

        robot2 = new ImageIcon("imagenes/zombi2.png");
        robot2 = new ImageIcon(robot2.getImage().getScaledInstance(50, 55, java.awt.Image.SCALE_SMOOTH));

        wallE = new Agente("Wall-E", robot1, matrix, tablero, coordenadasMothership);
        eva = new Agente("Eva", robot2, matrix, tablero, coordenadasMothership);
    }

    private void gestionaPause(ActionEvent eventObject) {
        wallE.pausar();
        eva.pausar();
        
        enPausa = true;
        ((JButton) eventObject.getSource()).setText("Reanudar");
        settings.setEnabled(true);
        randomButton.setEnabled(true);
    }
    
    private void gestionaReanudar(ActionEvent eventObject) {
        wallE.reanudar();
        eva.reanudar();
        
        enPausa = false;
        ((JButton) eventObject.getSource()).setText("En Pause");
        settings.setEnabled(false);
    }

    public void actualizaContadores() {
        obstaculosLabel.setText("Obstaculos: " + cantidadObstaculos);
        muestrasLabel.setText("Muestras: " + cantidadMuestras);
    }
    
    public static ImageIcon getMotherIcon (){
        return motherIcon;
    }
    
}
