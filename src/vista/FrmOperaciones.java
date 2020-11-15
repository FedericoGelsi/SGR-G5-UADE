package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import static javax.swing.JOptionPane.showMessageDialog;

public class FrmOperaciones extends JDialog{
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JTabbedPane pnlTabPanelOps;
    private JPanel pnlCHP;
    private JButton JBCHP;
    private JPanel pnlCHT;
    private JPanel pnlCCC;
    private JPanel pnlPB;
    private JPanel pnlTC;
    private JPanel pnlPST;
    private JComboBox comboBoxCDCPST;
    private JComboBox comboBEPST;
    private JLabel JLITPST;
    private JLabel JLTasaPST;
    private JLabel JLTDAPST;
    private JLabel comboCDCPST;
    private JSpinner spinnerITPST;
    private JLabel JLTitulo;
    private JLabel JLLogo;
    private JLabel JLEncabezadoPST;
    private JLabel JLEncabezadoTC;
    private JLabel JLBEPST;
    private JTextField TFFDAPST;
    private JButton JBPST;
    private JLabel JLSistemaPST;
    private JComboBox comboSistemaPST;
    private JTextField TFNDTTC;
    private JTextField TFNombreTC;
    private JTextField TFFVTC;
    private JTextField TFCDSTC;
    private JButton JBTC;
    private JTextField TFNDPPB;
    private JTextField TFFDVPB;
    private JTextField TFCDFPB;
    private JButton JBPB;
    private JComboBox comboBEPB;
    private JTextField TFITCCC;
    private JTextField TFFDVCCC;
    private JButton JBCCC;
    private JSpinner spinnerITCCC;
    private JTextField TFBECHT;
    private JTextField TFFDVCHT;
    private JTextField TFCDFCHT;
    private JButton JBCHT;
    private JComboBox comboCHT;
    private JTextField TFTDDCHP;
    private JTextField TFFDVCHP;
    private JTextField TFCDFCHP;
    private JComboBox comboBECHP;
    private JSpinner spinnerTDDCHP;

    //Compara una fecha entregada por parametro contra la fecha actual y devuelve Menor si la fecha ingresada es en el
    // pasado, Mayor si la fecha ingresada es en el futuro o Hoy si la fecha ingresada es la actual.
    public String fechavshoy(LocalDate fecha) {
        LocalDate hoy = LocalDate.now();
        int comparacion = fecha.compareTo(hoy);
        if (comparacion < 0) {
            return "Menor";
        }
        if (comparacion > 0) {
            return "Mayor";
        } else {
            return "Hoy";
        }
    }

    public boolean CUITValido(String CUIT) {
        String[] cuitseparado = CUIT.split("-");
        boolean CUITValidoflag = true;
        boolean allLetters0 = cuitseparado[0].chars().anyMatch(Character::isLetter);
        if (cuitseparado[0].length() != 2 || allLetters0==true) {
            CUITValidoflag = false;
        }
        boolean allLetters1 = cuitseparado[1].chars().anyMatch(Character::isLetter);
        if (cuitseparado[1].length() != 8 || allLetters1==true ){
            CUITValidoflag = false;
        }
        boolean allLetters2 = cuitseparado[2].chars().anyMatch(Character::isLetter);
        if (cuitseparado[2].length() != 1 || allLetters2==true){
            CUITValidoflag = false;
        }
        return CUITValidoflag;
    }
    //Chequea que un String este compuesto unicamente por numeros
    public boolean esnumerico(String datos){
        String regex = "[0-9]+";
        boolean numerico = datos.matches(regex);
        return numerico;
    }

    public boolean fechavalida(String fechacheck){
        String[] fechaseparada = fechacheck.split("/");
        boolean fechavalidaFlag = true;
        if (fechaseparada[0].length() != 2 || esnumerico(fechaseparada[0])!=true) {
            fechavalidaFlag = false;
        }
        if (fechaseparada[1].length() != 2 || esnumerico(fechaseparada[1])!=true) {
            fechavalidaFlag = false;
        }
        if (fechaseparada[2].length() != 4 || esnumerico(fechaseparada[2])!=true) {
            fechavalidaFlag = false;
        }
        return fechavalidaFlag;
    }

    public FrmOperaciones(Window owner, String Title) {
        super(owner, Title);
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
        this.setSize(1000, 600);
        // No permite volver a la pantalla anterior hasta cerrar esta
        this.setModal(true);
        // Establezco el comportamiento al cerrar la pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Inicia la pantalla centrada
        this.setLocationRelativeTo(null);

        //Action Listener de JButton Cheques Personales
        JBCHP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Toma el objeto Banco Emisor desde el Combo Box comboBECHP
                Object BECHP = new Object();
                BECHP = comboBECHP.getSelectedItem();

                //Toma el String Numero de Cheque desde el JText Field TFNCCHP y lo transforma en un entero
                String NCCHP;
                int NCCHPint;
                NCCHP = TFTDDCHP.getText();
                if (NCCHP.isEmpty()) {
                    showMessageDialog(null, "El campo Numero de Cheque es mandatorio, por favor ingrese el dato solicitado");
                }
                else {
                    if (esnumerico(NCCHP)) {
                        NCCHPint = Integer.parseInt(NCCHP);
                    } else {
                        showMessageDialog(null, "El campo Numero de Cheque solo admite numeros, no se admiten otros caracteres");
                    }
                }

                //Toma el String Fecha de Vencimiento desde el JText Field FDVCHP y lo transforma en un date
                String FDVCHP = "";
                FDVCHP = TFFDVCHP.getText();
                if (fechavalida(FDVCHP)==true) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(FDVCHP, formatter);
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = fechavshoy(localDate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "El cheque se encuentra vencido");
                    }
                    if (comparacionfecha == "Hoy") {
                        showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                    }
                }
                else{
                    showMessageDialog(null,"La fecha ingresada no cumple con el formato solicitado");
                    }

                //Toma el String CUIT firmante desde el JText Field TFCDF
                String CDFCHP = "";
                CDFCHP = TFCDFCHP.getText();
                if (CUITValido(CDFCHP)){
                }
                else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                }

                //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
                Object TDDCHP;
                TDDCHP = spinnerTDDCHP.getValue();
                int TDDCHPint;
                TDDCHPint= (Integer) TDDCHP;
                if(TDDCHPint <= 0){
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                }
                if(TDDCHPint >=100){
                    showMessageDialog(null,"El cheque no puede ser vendido con una tasa de descuento superior al 99%");
                }
            }
        });
    }
}
