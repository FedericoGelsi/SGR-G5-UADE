package vista;

import javax.swing.*;

public class FrmPrincipal extends JFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlMenu;
    private JButton sociosButton;
    private JButton líneasYTiposDeButton;
    private JButton operacionesButton;
    private JButton desembolsosYRecuperosButton;
    private JButton consultasGeneralesButton;

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
        //this.asociarEventos();

        this.self = this;
    }

    /*
    private void asociarEventos(){
        tabsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmTabsDemo frame = new FrmTabsDemo(self, "TABS menu");
                frame.setVisible(true);
            }
        });
    }
    */
    public static void main(String[] args) {
        FrmPrincipal frame = new FrmPrincipal("Sistema de Gestión de Sociedades de Garantías Recíprocas");
        //  Mostrar el panel
        frame.setVisible(true);
    }

}
