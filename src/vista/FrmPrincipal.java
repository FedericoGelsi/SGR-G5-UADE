package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmPrincipal extends JFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlMenu;
    private JButton sociosButton;
    private JButton líneasYTiposDeButton;
    private JButton operacionesButton;
    private JButton desembolsosYRecuperosButton;
    private JButton consultasGeneralesButton;
    private JPanel pnlTitulo;

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
        this.setSize(800,600);
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
                FrmSocios frame = new FrmSocios(self, "SGR - Socios");
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
    }
    public static void main(String[] args) {
        FrmPrincipal frame = new FrmPrincipal("Sistema de Gestión de Sociedades de Garantías Recíprocas");
        //  Mostrar el panel
        frame.setVisible(true);
    }

}
