package vista;

import api.API_JSONHandler;
import api.Verificaciones;
import impl.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class FrmOperaciones extends JDialog {
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
    private JLabel JLCDCPST;
    private JSpinner spinnerITPST;
    private JLabel JLTitulo;
    private JLabel JLLogo;
    private JLabel JLEncabezadoPST;
    private JLabel JLEncabezadoTC;
    private JLabel JLBEPST;
    private JTextField TFFDAPST;
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
    private JSpinner spinnerTDDPST;
    private JButton JBPST;
    private JLabel JLCSPST;
    private JTextField TFCSPST;
    private JComboBox comboBECHT;
    private JTextField TFNDCCHT;
    private JTextField TFNDCCHP;
    private JSpinner spinnerITCHP;
    private JSpinner spinnerITCHT;
    private JSpinner spinnerITPB;
    private JTextField JTFCSCHT;
    private JTextField JTFCSPB;
    private JSpinner spinnerTDDCHT;
    private JSpinner spinnerTDDPB;
    private JComboBox comboNDOC;
    private JButton JBC;
    private JPanel pnlComision;
    private JTextField TFCUIT;
    private JTextField TFCUIT2;
    private JLabel TIPOTARJETA;
    private JLabel Image;
    private Verificaciones verif = new impl.Verificaciones();

    private String filename = "./src/resources/socios.json";
    private API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);

    private String filenamefact = "./src/resources/operacioncontroller.json";
    private JSONObject jsonObjectOPC = (JSONObject) file.readJson(filenamefact);


    public FrmOperaciones(Window owner, String Title) throws Exception {
        super(owner, Title);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
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


        verif.crearFacturas(); // CAMBIAR A LOGIN

        //Action Listener de JButton Cheques Personales
        JBCHP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagCHP = true;
                float totalmenostasa = 0;
                //Toma el objeto Banco Emisor desde el Combo Box comboBECHP
                Object BECHP = new Object();
                BECHP = comboBECHP.getSelectedItem();

                //Toma el String Numero de Cheque desde el JText Field TFNCCHP y lo transforma en un entero
                String NCCHP;
                int NCCHPint = 0;
                NCCHP = TFNDCCHP.getText();
                if (NCCHP.isEmpty()) {
                    showMessageDialog(null, "El campo Numero de Cheque es mandatorio, por favor ingrese el dato solicitado");
                    DatosCorrectosFlagCHP = false;
                } else {
                    if (verif.esnumerico(NCCHP)) {
                        NCCHPint = Integer.parseInt(NCCHP);
                    } else {
                        showMessageDialog(null, "El campo Numero de Cheque solo admite numeros, no se admiten otros caracteres");
                        DatosCorrectosFlagCHP = false;
                    }
                }

                //Toma el String Fecha de Vencimiento desde el JText Field FDVCHP y lo transforma en un date
                String FDVCHP = "";
                FDVCHP = TFFDVCHP.getText();
                String FDVCHPaux = "20/04/1989";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate FDVCHPdateaux = LocalDate.parse(FDVCHPaux, formatter);
                if (verif.fechavalida(FDVCHP) == true) {
                    LocalDate FDVCHPdate = LocalDate.parse(FDVCHP, formatter);
                    FDVCHPdateaux = FDVCHPdate;
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = verif.fechavshoy(FDVCHPdate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "El cheque se encuentra vencido");
                        DatosCorrectosFlagCHP = false;
                    }
                    if (comparacionfecha == "Hoy") {
                        showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                        DatosCorrectosFlagCHP = false;
                    }
                } else {
                    showMessageDialog(null, "La fecha ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagCHP = false;
                }

                //Toma el String CUIT firmante desde el JText Field TFCDFCHT
                String CDFCHP = "";
                CDFCHP = TFCDFCHP.getText();
                if (verif.CUITValido(CDFCHP)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagCHP = false;
                }

                //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
                Object TDDCHP;
                TDDCHP = spinnerTDDCHP.getValue();
                int TDDCHPint;
                TDDCHPint = (Integer) TDDCHP;
                if (TDDCHPint <= 0) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                    DatosCorrectosFlagCHP = false;
                }
                if (TDDCHPint >= 100) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento superior al 99%");
                    DatosCorrectosFlagCHP = false;
                }

                //Toma el Entero Importe Total desde el JSpinner spinnerITCHP
                Object ITCHP;
                ITCHP = spinnerITCHP.getValue();
                int ITCHPint;
                ITCHPint = (Integer) ITCHP;
                if (ITCHPint <= 0) {
                    showMessageDialog(null, "El cheque no puede tener un valor menor o igual a 0");
                    DatosCorrectosFlagCHP = false;
                }

                if (DatosCorrectosFlagCHP == true) {
                    boolean checks = true;
                    if (verif.lineacreditovigente(CDFCHP) == false) {
                        showMessageDialog(null, "La linea de credito se encuentra vencida");
                        checks = false;
                    }
                    if (verif.debefacturas(CDFCHP) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que se adeudan facturas");
                        checks = false;
                    }
                    ArrayList<String> EmpresaComparteSocio = new ArrayList<>();

                    EmpresaComparteSocio = verif.ListaCUITAC(CDFCHP);
                    if(!EmpresaComparteSocio.isEmpty()) {
                        if(verif.Computar5FDRAc(EmpresaComparteSocio,CDFCHP,ITCHPint)==true){
                            showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que supera la suma de los riesgos vivos");
                            checks = false;
                        }
                    }
                    else{
                        if (verif.operacionvsfdr(ITCHPint) == false) {
                            checks = false;
                            showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor que el 5% del fondo de riesgo");

                        }
                    }
                    if(verif.check_deuda(CDFCHP)==true){
                        showMessageDialog(null,"No puede operar porque el socio tiene deudas");
                        checks = false;
                    }

                    if (checks == true) {
                        int mensaje_numcertificado = 0;
                        totalmenostasa=(float)ITCHPint-((float) ITCHPint *((float) TDDCHPint/100));
                        try {
                            mensaje_numcertificado=verif.crearOT1(FDVCHPdateaux, BECHP.toString(), NCCHPint, CDFCHP, TDDCHPint, CDFCHP, "Cheque Propio", totalmenostasa, "Ingresado");
                            showMessageDialog(null,"Su operación fue realizada con éxito, el numero del certificado de garantia es: " +mensaje_numcertificado);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        });
        JBCHT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagCHT = true;
                float totalmenostasacht=0;
                //Toma el objeto Banco Emisor desde el Combo Box comboBECHT
                Object BECHT = new Object();
                BECHT = comboBECHT.getSelectedItem();

                //Toma el String Numero de Cheque desde el JText Field TFNCCHT y lo transforma en un entero
                String NCCHT;
                int NCCHTint = 0;
                NCCHT = TFNDCCHT.getText();
                if (NCCHT.isEmpty()) {
                    showMessageDialog(null, "El campo Numero de Cheque es mandatorio, por favor ingrese el dato solicitado");
                    DatosCorrectosFlagCHT = false;
                } else {
                    if (verif.esnumerico(NCCHT)) {
                        NCCHTint = Integer.parseInt(NCCHT);
                    } else {
                        showMessageDialog(null, "El campo Numero de Cheque solo admite numeros, no se admiten otros caracteres");
                        DatosCorrectosFlagCHT = false;
                    }
                }
                //Toma el String Fecha de Vencimiento desde el JText Field FDVCHT y lo transforma en un date
                String FDVCHT = "";
                FDVCHT = TFFDVCHT.getText();
                String FDVCHTaux = "20/04/1989";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate FDVCHTdateaux = LocalDate.parse(FDVCHTaux, formatter);
                if (verif.fechavalida(FDVCHT) == true) {
                    LocalDate FDVCHPdate = LocalDate.parse(FDVCHT, formatter);
                    FDVCHTdateaux = FDVCHPdate;
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = verif.fechavshoy(FDVCHPdate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "El cheque se encuentra vencido");
                        DatosCorrectosFlagCHT = false;
                    }
                    if (comparacionfecha == "Hoy") {
                        showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                        DatosCorrectosFlagCHT = false;
                    }
                } else {
                    showMessageDialog(null, "La fecha ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagCHT = false;
                }
                //Toma el String CUIT firmante desde el JText Field TFCDFCHT
                String CDFCHT = "";
                CDFCHT = TFCDFCHT.getText();
                if (verif.CUITValido(CDFCHT)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagCHT = false;
                }
                //Toma el Entero Importe Total desde el JSpinner spinnerITCHT
                Object ITCHT;
                ITCHT = spinnerITCHT.getValue();
                int ITCHTint;
                ITCHTint = (Integer) ITCHT;
                if (ITCHTint <= 0) {
                    showMessageDialog(null, "El cheque no puede tener un valor menor o igual a 0");
                    DatosCorrectosFlagCHT = false;
                }

                //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
                Object TDDCHT;
                TDDCHT = spinnerTDDCHT.getValue();
                int TDDCHTint;
                TDDCHTint = (Integer) TDDCHT;
                if (TDDCHTint <= 0) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                    DatosCorrectosFlagCHT = false;
                }
                if (TDDCHTint >= 100) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento superior al 99%");
                    DatosCorrectosFlagCHT = false;
                }

                //Toma el String CUIT Socio desde el JTFCSPB
                String CSCHT;
                CSCHT = JTFCSCHT.getText();
                if (verif.CUITValido(CDFCHT)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagCHT = false;
                }
                if (verif.essocioparticipe(CSCHT) == false) {
                    showMessageDialog(null, "El socio ingresado no es un Socio Participe Pleno");
                    DatosCorrectosFlagCHT = false;
                }
                if (DatosCorrectosFlagCHT == true) {
                    boolean checks = true;
                    if (verif.lineacreditovigente(CSCHT) == false) {
                        showMessageDialog(null, "La linea de credito se encuentra vencida");
                        checks = false;
                    }
                    if (verif.contragarantiassuficientes(CSCHT, ITCHTint) == false) {
                        showMessageDialog(null, "Las contragarantias del socio son insuficientes para realizar esta operacion");
                        checks = false;
                    }
                    if (verif.lineasuficiente(CSCHT, ITCHTint) == false) {
                        showMessageDialog(null, "La linea de credito no tiene disponibilidad para realizar esta operacion");
                        checks = false;
                    }
                    if (verif.debefacturas(CSCHT) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que se adeudan facturas");
                        checks = false;
                    }
                    if (verif.operacionvsfdr(ITCHTint) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor que el 5% del fondo de riesgo");
                        checks = false;
                    }
                    if(verif.check_deuda(CSCHT)==true){
                        showMessageDialog(null,"No puede operar porque el socio tiene deudas");
                        checks = false;
                    }
                    if (checks == true) {
                        int mensaje_numcertificado = 0;
                        try {
                            totalmenostasacht=(float)ITCHTint-((float) ITCHTint *((float) TDDCHTint/100));
                            mensaje_numcertificado=verif.crearOT1(FDVCHTdateaux, BECHT.toString(), NCCHTint, CDFCHT, TDDCHTint, CSCHT, "Cheque de terceros", totalmenostasacht, "Ingresado");
                            showMessageDialog(null,"Su operación fue realizada con éxito, el numero del certificado de garantia es: " +mensaje_numcertificado);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        });
        JBPB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagPB = true;
                float totalmenostasapb=0;
                //Toma el objeto Banco Emisor desde el Combo Box comboBEPB
                Object BEPB = new Object();
                BEPB = comboBEPB.getSelectedItem();

                //Toma el String Numero de pagare desde el JText Field TFNDPPB y lo transforma en un entero
                String NDPPB;
                int NDPPBint = 0;
                NDPPB = TFNDPPB.getText();
                if (NDPPB.isEmpty()) {
                    showMessageDialog(null, "El campo Numero de pagare es mandatorio, por favor ingrese el dato solicitado");
                    DatosCorrectosFlagPB = false;
                } else {
                    if (verif.esnumerico(NDPPB)) {
                        NDPPBint = Integer.parseInt(NDPPB);
                    } else {
                        showMessageDialog(null, "El campo Numero de pagare solo admite numeros, no se admiten otros caracteres");
                        DatosCorrectosFlagPB = false;
                    }
                }
                //Toma el String Fecha de Vencimiento desde el JText Field FDVPB y lo transforma en un date
                String FDVPB = "";
                FDVPB = TFFDVPB.getText();
                String FDVPBaux = "20/04/1989";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate FDVPBdateaux = LocalDate.parse(FDVPBaux, formatter);
                if (verif.fechavalida(FDVPB) == true) {
                    LocalDate FDVPBdate = LocalDate.parse(FDVPB, formatter);
                    FDVPBdateaux = FDVPBdate;
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un pagare el dia de su
                    // canje o vender un pagare ya vencido.
                    String comparacionfecha = verif.fechavshoy(FDVPBdate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "El pagare bursatil se encuentra vencido");
                        DatosCorrectosFlagPB = false;
                    }
                    if (comparacionfecha == "Hoy") {
                        showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                        DatosCorrectosFlagPB = false;
                    }
                } else {
                    showMessageDialog(null, "La fecha ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagPB = false;
                }
                //Toma el String CUIT firmante desde el JText Field TFCDFPB
                String CDFPB = "";
                CDFPB = TFCDFPB.getText();
                if (verif.CUITValido(CDFPB)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagPB = false;
                }
                //Toma el Entero Importe Total desde el JSpinner spinnerITPB
                Object ITPB;
                ITPB = spinnerITPB.getValue();
                int ITPBint;
                ITPBint = (Integer) ITPB;
                if (ITPBint <= 0) {
                    showMessageDialog(null, "El pagare bursatil no puede tener un valor menor o igual a 0");
                    DatosCorrectosFlagPB = false;
                }

                //Toma el Entero Tasa desde el JSpinner spinnerTDDPB
                Object TDDPB;
                TDDPB = spinnerTDDPB.getValue();
                int TDDPBint;
                TDDPBint = (Integer) TDDPB;
                if (TDDPBint <= 0) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                    DatosCorrectosFlagPB = false;
                }
                if (TDDPBint >= 100) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento superior al 99%");
                    DatosCorrectosFlagPB = false;
                }

                //Toma el String CUIT Socio desde el JTFCSPB
                String CSPB;
                CSPB = JTFCSPB.getText();
                if (verif.CUITValido(CSPB)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagPB = false;
                }
                if (verif.essocioparticipe(CSPB) == false) {
                    showMessageDialog(null, "El socio ingresado no es un Socio Participe Pleno");
                    DatosCorrectosFlagPB = false;
                }

                if (DatosCorrectosFlagPB == true) {
                    boolean checks = true;
                    if (verif.lineacreditovigente(CSPB) == false) {
                        showMessageDialog(null, "La linea de credito se encuentra vencida");
                        checks = false;
                    }
                    if (verif.lineasuficiente(CSPB, ITPBint) == false) {
                        showMessageDialog(null, "La linea de credito no tiene disponibilidad para realizar esta operacion");
                        checks = false;
                    }
                    if (verif.debefacturas(CSPB) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que se adeudan facturas");
                        checks = false;
                    }
                    if (verif.operacionvsfdr(ITPBint) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor que el 5% del fondo de riesgo");
                        checks = false;
                    }
                    if(verif.check_deuda(CSPB)==true){
                        showMessageDialog(null,"No puede operar porque el socio tiene deudas");
                        checks = false;
                    }
                    if (checks == true) {
                        int mensaje_numcertificado = 0;
                        try {
                            totalmenostasapb=(float)ITPBint-((float) ITPBint *((float) TDDPBint/100));
                            mensaje_numcertificado=verif.crearOT1(FDVPBdateaux, BEPB.toString(), NDPPBint, CDFPB, TDDPBint, CSPB, "Pagare Bursatil", totalmenostasapb, "Ingresado");
                            showMessageDialog(null,"Su operación fue realizada con éxito, el numero del certificado de garantia es: " +mensaje_numcertificado);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        });


        // El actionListener de Prestamos
        JBPST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagPST = true;
                //Toma el objeto Banco Emisor desde el Combo Box comboBECHP
                Object BEPST = new Object();
                BEPST = comboBEPST.getSelectedItem();

                //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
                Object TDDPST;
                TDDPST = spinnerTDDPST.getValue();
                int TDDPSTint;
                TDDPSTint = (Integer) TDDPST;
                if (TDDPSTint <= 0) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                    DatosCorrectosFlagPST = false;
                }


                //Toma el importe total
                Object ITPST;
                ITPST = spinnerITPST.getValue();
                int ITFloatPST;
                ITFloatPST = (Integer) ITPST;
                if (ITFloatPST <= 0) {
                    showMessageDialog(null, "El importe total del prestamo no puede ser igual o menor que 0");
                    DatosCorrectosFlagPST = false;
                }

                //Toma el String Fecha de Vencimiento desde el JText Field FDVPB y lo transforma en un date
                String FDVPB = "";
                FDVPB = TFFDAPST.getText();
                String FDVPBaux = "20/04/1989";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate FDVPBdateaux = LocalDate.parse(FDVPBaux, formatter);
                if (verif.fechavalida(FDVPB) == true) {
                    LocalDate FDVPBdate = LocalDate.parse(FDVPB, formatter);
                    FDVPBdateaux = FDVPBdate;
                    String comparacionfecha = verif.fechavshoy(FDVPBdate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "La fecha de acreditación del prestamo debe ser en el presente o en el futuro");
                        DatosCorrectosFlagPST = false;
                    }
                } else {
                    showMessageDialog(null, "La fecha ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagPST = false;
                }

                //Toma el String CUIT Socio desde el JText Field TFCSPST
                String CSPST = "";
                CSPST = TFCSPST.getText();
                if (verif.CUITValido(CSPST)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagPST = false;
                }

                //Toma el objeto Sistema desde el Combo Box comboSistemaPST
                Object Sistema_PST = new Object();
                Sistema_PST = comboSistemaPST.getSelectedItem();

                //Toma el objeto Cantidad de cuotas desde el Combo Box comboSistemaPST
                Object cantidadCuotas = new Object();
                cantidadCuotas = comboBoxCDCPST.getSelectedItem();


                if (verif.essocioparticipe(CSPST) == false) {
                    showMessageDialog(null, "El socio ingresado no es un Socio Participe Pleno");
                    DatosCorrectosFlagPST = false;
                }

                if (DatosCorrectosFlagPST == true) {
                    boolean checks = true;
                    if (verif.lineacreditovigente(CSPST) == false) {
                        showMessageDialog(null, "La linea de credito se encuentra vencida");
                        checks = false;
                    }
                    if (verif.lineasuficiente(CSPST, ITFloatPST) == false) {
                        showMessageDialog(null, "La linea de credito no tiene disponibilidad para realizar esta operacion");
                        checks = false;
                    }
                    if (verif.debefacturas(CSPST) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que se adeudan facturas");
                        checks = false;
                    }
                    if (verif.operacionvsfdr(ITFloatPST) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor que el 5% del fondo de riesgo");
                        checks = false;
                    }
                    if(verif.check_deuda(CSPST)==true){
                        showMessageDialog(null,"No puede operar porque el socio tiene deudas");
                        checks = false;
                    }
                    if (checks == true) {
                        int mensaje_numcertificado = 0;
                        try {
                            mensaje_numcertificado=verif.crearOT3(cantidadCuotas.toString(), BEPST.toString(), ITFloatPST, TDDPSTint, Sistema_PST.toString(), FDVPBdateaux, CSPST, "Ingresado", "Prestamo");
                            showMessageDialog(null,"Su operación fue realizada con éxito, el numero del certificado de garantia es: " +mensaje_numcertificado);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        });

        pnlTabPanelOps.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //Popula el comboNDOC
                JSONObject jsonObjectOPC = null;
                try {
                    jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                comboNDOC.removeAllItems();
                String estadoNDOC = "";
                int numeroopNDOC = 0;
                int numeroopcdgNDOC = 0;
                int numerocertificado=0;
                ArrayList<Integer> listacertificados = new ArrayList<Integer>();

                JSONArray operacioneslist = (JSONArray) jsonObjectOPC.get("operaciones");
                for (Object ops : operacioneslist) {
                    JSONObject operacion = (JSONObject) ops;
                    estadoNDOC = operacion.get("estado").toString();
                    if (estadoNDOC.equalsIgnoreCase("Con certificado emitido")) {
                        numeroopNDOC = Integer.parseInt(operacion.get("numerooperacion").toString());
                        JSONArray certdegarlist = (JSONArray) jsonObjectOPC.get("certificado-de-garantia");
                        for (Object cdg : certdegarlist) {
                            JSONObject certificado = (JSONObject) cdg;
                            numeroopcdgNDOC = Integer.parseInt(certificado.get("numero-operacion").toString());
                            if (numeroopNDOC==numeroopcdgNDOC) {
                                numerocertificado=Integer.parseInt(certificado.get("idcertificado").toString());
                                comboNDOC.addItem(numerocertificado);
                            }
                        }
                    }
                }
            }
        });

        JBC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double montocomision=0;
                int numerooperacion =  Integer.parseInt (comboNDOC.getSelectedItem().toString());
                try {
                    montocomision=verif.nuevacomision(numerooperacion);
                    showMessageDialog(null,"El monto de la comision es: "+montocomision);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                //Popula el comboNDOC
                JSONObject jsonObjectOPC = null;
                try {
                    jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                comboNDOC.removeAllItems();
                String estadoNDOC = "";
                int numeroopNDOC = 0;
                int numeroopcdgNDOC = 0;
                int numerocertificado=0;
                ArrayList<Integer> listacertificados = new ArrayList<Integer>();

                JSONArray operacioneslist = (JSONArray) jsonObjectOPC.get("operaciones");
                for (Object ops : operacioneslist) {
                    JSONObject operacion = (JSONObject) ops;
                    estadoNDOC = operacion.get("estado").toString();
                    if (estadoNDOC.equalsIgnoreCase("Con certificado emitido")) {
                        numeroopNDOC = Integer.parseInt(operacion.get("numerooperacion").toString());
                        JSONArray certdegarlist = (JSONArray) jsonObjectOPC.get("certificado-de-garantia");
                        for (Object cdg : certdegarlist) {
                            JSONObject certificado = (JSONObject) cdg;
                            numeroopcdgNDOC = Integer.parseInt(certificado.get("numero-operacion").toString());
                            if (numeroopNDOC==numeroopcdgNDOC) {
                                numerocertificado=Integer.parseInt(certificado.get("idcertificado").toString());
                                comboNDOC.addItem(numerocertificado);
                            }
                        }
                    }
                }
            }
        });
        JBCCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagCCC = true;
                String FDVCCC = "";
                FDVCCC = TFFDVCCC.getText();
                if (verif.fechavalida(FDVCCC)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(FDVCCC, formatter);
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = verif.fechavshoy(localDate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "La fecha de acreditación no es valida");
                        DatosCorrectosFlagCCC = false;
                    }
                } else {
                    showMessageDialog(null, "La fecha ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagCCC = false;
                }
                //Toma el String CUIT firmante desde el JText Field TFCDFCHT
                String CUIT = "";
                CUIT = TFCUIT.getText();
                if (verif.CUITValido(CUIT)){
                }
                else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagCCC = false;
                }
                //Toma el String Numero de Cheque desde el JText Field TFNCCHP y lo transforma en un entero
                String ITCCC;
                int ITCCCint;
                ITCCC = TFITCCC.getText();
                if (ITCCC.isEmpty()) {
                    showMessageDialog(null, "El campo Empresa es mandatorio, por favor ingrese el dato solicitado");
                    DatosCorrectosFlagCCC = false;
                }
                Object SITCCC;
                SITCCC = spinnerITCCC.getValue();
                int SITCCCint;
                SITCCCint= (Integer) SITCCC;
                if(SITCCCint <= 0){
                    showMessageDialog(null, "El importe total debe ser mayor que 0");
                    DatosCorrectosFlagCCC = false;
                }




            }
        });
        JBTC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagTC = true;
                String NombreTC;
                int NombreTCint;
                NombreTC = TFNombreTC.getText();
                if (NombreTC.isEmpty()) {
                    showMessageDialog(null, "Debe ingresar el nombre completo tal como figura en la tarjeta");
                    DatosCorrectosFlagTC = false;
                }
                if(verif.esnumerico(NombreTC)){
                    showMessageDialog(null,"El nombre del titular no puede contener números");
                    DatosCorrectosFlagTC = false;
                }
                String FVTC = "";
                FVTC = TFFVTC.getText();
                if(verif.fechavalidatarjeta(FVTC) == true) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                    YearMonth yearMonth = YearMonth.parse(FVTC, formatter);
                    String comparacionfecha = verif.fechavshoytarjeta(yearMonth);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "La tarjeta se encuentra vencida");
                        DatosCorrectosFlagTC = false;
                    }

            }
                else {
                    showMessageDialog(null, "La fecha de vencimiento ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagTC = false;}

                String CDSTC;
                int CDSTCint;
                CDSTC = TFCDSTC.getText();
                if (CDSTC.isEmpty()){
                    showMessageDialog(null, "El código no puede quedar vacío");
                    DatosCorrectosFlagTC = false;
                }

                if (verif.esnumerico(CDSTC)){
                    if (CDSTC.length() != 3 && TIPOTARJETA.getText().equals("VISA") || CDSTC.length() != 3 && TIPOTARJETA.getText().equals("MASTERCARD")){
                        showMessageDialog(null, "El código de seguridad para una tarjeta VISA o MASTERCARD debe ser de 3 números");
                    }
                    if(CDSTC.length() != 4 && TIPOTARJETA.getText().equals("AMERICAN")){
                        showMessageDialog(null, "El código de seguridad para una tarjeta AMERICAN EXPRESS debe ser de 4 números");
                    }
                }
                if(!verif.esnumerico(CDSTC) && !CDSTC.isEmpty()){
                    showMessageDialog(null,"El código debe ser numérico");
                    DatosCorrectosFlagTC = false;
                }
                String CUIT2 = "";
                CUIT2 = TFCUIT2.getText();
                if (verif.CUITValido(CUIT2)){
                }
                else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagTC = false;
                }
                String NDTTC;
                int TFNDTTCint;
                NDTTC = TFNDTTC.getText();
                if (NDTTC.contains("-") && NDTTC.length() >= 17){
                    if (verif.tarjetavalida(NDTTC, TIPOTARJETA)){
                    }
                    else {
                        showMessageDialog(null, "La tarjeta ingresada es inválida");
                        DatosCorrectosFlagTC = false;
                    }
                }
                else{
                    if (NDTTC.isEmpty()){
                        showMessageDialog(null,"Ingrese el número de la tarjeta");
                    }
                    else{
                        showMessageDialog(null,"El número de tarjeta debe contener '-' y al menos 15 números");

                    }

                }

            }

    });
        TFNDTTC.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String NDTTC;
                int TFNDTTCint;
                NDTTC = TFNDTTC.getText();
                if (NDTTC.length() > 1){
                    if(verif.isMaster(NDTTC)){
                        TIPOTARJETA.setText("MASTERCARD");
                        Image.setIcon(new ImageIcon("image/mastercard.png"));
                    }
                    if(verif.isVisa(NDTTC)){
                        TIPOTARJETA.setText("VISA");
                        Image.setIcon(new ImageIcon("image/visa.png"));
                    }
                    if (verif.isAmerican(NDTTC)){
                        TIPOTARJETA.setText("AMERICAN");
                        Image.setIcon(new ImageIcon("image/american.png"));
                    }
                }
                else{
                    TIPOTARJETA.setText("-");
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Image = new JLabel(new ImageIcon());
    }
}
