package vista;

import api.Aporte;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

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
    private JPanel pnlPane;
    private JPanel pnlLogin;
    private JLabel bgImg;
    private JLabel loggedAs;

    private FrmPrincipal self;

    public FrmPrincipal(String Title, String CUIT){
        super(Title);

        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
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
        this.loggedAs.setText(CUIT);
        this.asociarEventos();
        this.self = this;



    }
    private void asociarEventos(){
        sociosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmSocios frame = null;
                try {
                    frame = new FrmSocios(self,"SGR - Socios");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                self.setVisible(false);
                frame.setVisible(true);
                self.setVisible(true);
            }
        });
        líneasYTiposDeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmLytiposOP frame = null;
                try {
                    frame = new FrmLytiposOP(self, "SGR - Líneas y tipos de Operaciones asociadas");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                self.setVisible(false);
                frame.setVisible(true);
                self.setVisible(true);
            }
        });
        operacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmOperaciones frame = null;
                try {
                    frame = new FrmOperaciones(self, "SGR - Operaciones");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                frame.setVisible(true);
                self.setVisible(true);
            }
        });
        desembolsosYRecuperosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmDyR frame = null;
                try {
                    frame = new FrmDyR(self, "SGR - Desembolsos y Recuperos");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                self.setVisible(false);
                frame.setVisible(true);
                self.setVisible(true);
            }
        });
        consultasGeneralesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmConsultasGenerales frame = null;
                try {
                    frame = new FrmConsultasGenerales(self, "SGR - Consultas Generales");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                self.setVisible(false);
                frame.setVisible(true);
                self.setVisible(true);
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
}
