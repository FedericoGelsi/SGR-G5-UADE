package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class FrmPrincipal extends JFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlMenu;
    private JButton sociosButton;
    private JButton líneasYTiposDeButton;
    private JButton operacionesButton;
    private JButton desembolsosYRecuperosButton;
    private JButton consultasGeneralesButton;
    private JPanel pnlTitulo;
    private JLabel iconLbl;
    private JButton button1;

    private FrmPrincipal self;

    public FrmPrincipal(String Title){
        super(Title);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Define el canvas según swing.
        this.setContentPane(this.pnlPrincipal);
        // Tamaño de la pantalla
        this.setSize(1000,600);
        // Evita que se modifique el tamaño de la pantalla
        this.setResizable(false);
        // Establezco el comportamiento al cerrar la pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Inicia la pantalla centrada
        this.setLocationRelativeTo(null);

        this.asociarEventos();
        this.self = this;



    }
    private void asociarEventos(){
        sociosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmSocios frame = new FrmSocios(self,"SGR - Socios");
                frame.setVisible(true);
            }
        });
        líneasYTiposDeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmLytiposOP frame = new FrmLytiposOP(self, "SGR - Líneas y tipos de Operaciones asociadas");
                frame.setVisible(true);
            }
        });
        operacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmOperaciones frame = new FrmOperaciones(self, "SGR - Operaciones");
                frame.setVisible(true);
            }
        });
        desembolsosYRecuperosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmDyR frame = new FrmDyR(self, "SGR - Desembolsos y Recuperos");
                frame.setVisible(true);
            }
        });
        consultasGeneralesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmConsultasGenerales frame = new FrmConsultasGenerales(self, "SGR - Consultas Generales");
                frame.setVisible(true);
            }
        });


        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                consultasGeneralesButton.setBackground(Color.decode("#445D7A"));
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                consultasGeneralesButton.setBackground(Color.decode("#364f6b"));
                repaint();

            }
        };
        consultasGeneralesButton.addMouseListener(listener);
        MouseAdapter listener1 = new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                desembolsosYRecuperosButton.setBackground(Color.decode("#445D7A"));
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                desembolsosYRecuperosButton.setBackground(Color.decode("#364f6b"));
                repaint();

            }
        };
        desembolsosYRecuperosButton.addMouseListener(listener1);
        MouseAdapter listener2 = new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                operacionesButton.setBackground(Color.decode("#445D7A"));
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                operacionesButton.setBackground(Color.decode("#364f6b"));
                repaint();

            }
        };
        operacionesButton.addMouseListener(listener2);
        MouseAdapter listener3 = new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                líneasYTiposDeButton.setBackground(Color.decode("#445D7A"));
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                líneasYTiposDeButton.setBackground(Color.decode("#364f6b"));
                repaint();

            }
        };
        líneasYTiposDeButton.addMouseListener(listener3);
        MouseAdapter listener4 = new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sociosButton.setBackground(Color.decode("#445D7A"));
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                sociosButton.setBackground(Color.decode("#364f6b"));
                repaint();

            }
        };
        sociosButton.addMouseListener(listener4);
    }
    public static void main(String[] args) {
        FrmPrincipal frame = new FrmPrincipal("Sistema de Gestión de Sociedades de Garantías Recíprocas");
        //  Mostrar el panel
        frame.setVisible(true);
    }

}
