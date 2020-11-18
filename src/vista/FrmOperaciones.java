package vista;

import api.API_JSONHandler;
import api.Verificaciones;
import impl.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JSpinner ITTC;
    private final Verificaciones verif = new impl.Verificaciones();

    private final String filename = "./src/resources/socios.json";
    private final API_JSONHandler file = new JSONHandler();
    private final JSONObject jsonObject = (JSONObject) file.readJson(filename);

    private final String filenamefact = "./src/resources/operacioncontroller.json";
    private final JSONObject jsonObjectOPC = (JSONObject) file.readJson(filenamefact);


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
                boolean checks = true;
                boolean DatosCorrectosFlagCHP = true;
                float totalmenostasa = 0;
                int ITCHPint = 0;
                int TDDCHPint = 0;
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
                    checks = false;
                } else {
                    if (verif.esnumerico(NCCHP)) {
                        NCCHPint = Integer.parseInt(NCCHP);
                    } else {
                        showMessageDialog(null, "El campo Numero de Cheque solo admite numeros, no se admiten otros caracteres");
                        DatosCorrectosFlagCHP = false;
                        checks = false;
                    }
                }

                //Toma el String Fecha de Vencimiento desde el JText Field FDVCHP y lo transforma en un date
                String FDVCHP = "";
                FDVCHP = TFFDVCHP.getText();
                LocalDate FDVCHPdate = LocalDate.parse(FDVCHP);
                if (FDVCHP.isEmpty()) {
                    showMessageDialog(null, "El campo fecha de vencimiento es mandatorio y no puede estar vacio");
                    DatosCorrectosFlagCHP = false;
                    checks = false;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                if (verif.fechavalida(FDVCHP) == false) {
                    showMessageDialog(null, "La fecha ingresada es invalida");
                    DatosCorrectosFlagCHP = false;
                    checks = false;
                } else {
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = verif.fechavshoy(FDVCHPdate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "El cheque se encuentra vencido");
                        DatosCorrectosFlagCHP = false;
                        checks = false;
                    }
                    if (comparacionfecha == "Hoy") {
                        showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                        DatosCorrectosFlagCHP = false;
                        checks = false;
                    }
                }

                //Toma el String CUIT firmante desde el JText Field TFCDFCHT
                String CDFCHP = "";
                CDFCHP = TFCDFCHP.getText();
                if (CDFCHP.equalsIgnoreCase("")) {
                    showMessageDialog(null, "El campo CUIT del firmante es mandatorio y no puede estar vacio");
                    DatosCorrectosFlagCHP = false;
                    checks = false;
                }
                if (verif.CUITValido(CDFCHP) != true) {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagCHP = false;
                    checks = false;
                }

                //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
                Object TDDCHP;
                TDDCHP = spinnerTDDCHP.getValue();
                if (TDDCHP.equals(null)) {
                    showMessageDialog(null, "El campo Tasa de descuento es mandatorio y no puede estar vacio");
                    DatosCorrectosFlagCHP = false;
                    checks = false;
                } else {
                    TDDCHPint = (Integer) TDDCHP;
                    if (TDDCHPint <= 0) {
                        showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                        DatosCorrectosFlagCHP = false;
                        checks = false;
                    }
                    if (TDDCHPint >= 100) {
                        showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento superior al 99%");
                        DatosCorrectosFlagCHP = false;
                        checks = false;
                    }
                }

                //Toma el Entero Importe Total desde el JSpinner spinnerITCHP
                Object ITCHP;
                ITCHP = spinnerITCHP.getValue();
                if (ITCHP.equals(null)) {
                    showMessageDialog(null, "El campo Importe Total es mandatorio y no puede estar vacio");
                    DatosCorrectosFlagCHP = false;
                    checks = false;
                } else {
                    ITCHPint = (Integer) ITCHP;
                    if (ITCHPint <= 0) {
                        showMessageDialog(null, "El cheque no puede tener un valor menor o igual a 0");
                        DatosCorrectosFlagCHP = false;
                        checks = false;
                    }
                }

                ArrayList<String> EmpresaComparteSocio = new ArrayList<>();
                EmpresaComparteSocio = verif.ListaCUITAC(CDFCHP);
                boolean fdrflag = false;
                if (DatosCorrectosFlagCHP == true) {
                    if (verif.essocioparticipe(CDFCHP) != true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio no es un socio participe pleno");
                        checks = false;
                    } else if (verif.lineacreditovigente(CDFCHP) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que la linea de credito se encuentra vencida");
                        checks = false;
                    } else if (verif.debefacturas(CDFCHP) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio adeuda facturas");
                        checks = false;
                    } else if (verif.check_deuda(CDFCHP) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio registra deudas");
                        checks = false;
                    } else if (!EmpresaComparteSocio.isEmpty()) {
                        if (verif.Computar5FDRAc(EmpresaComparteSocio, CDFCHP, ITCHPint) == true) {
                            showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que supera la suma de los riesgos vivos de los socios que comparten accionistas");
                            checks = false;
                            fdrflag = true;
                        }
                    } else if (verif.lineatope(CDFCHP, ITCHPint) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el importe total excede el tope de la linea de credito asignada");
                        checks = false;
                    } else if (verif.contragarantiassuficientes(CDFCHP, ITCHPint) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al total de las contragarantias presentadas");
                        checks = false;
                    } else if (fdrflag != true) {
                        try {
                            if (verif.operacionvsfdr(ITCHPint) == false) {
                                showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al 5% del fondo de riesgo");
                                checks = false;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }

                    if (checks == true) {
                        int mensaje_numcertificado = 0;
                        totalmenostasa = (float) ITCHPint - ((float) ITCHPint * ((float) TDDCHPint / 100));
                        try {
                            mensaje_numcertificado = verif.crearOT1(FDVCHPdate, BECHP.toString(), NCCHPint, CDFCHP, TDDCHPint, CDFCHP, "Cheque Propio", totalmenostasa, "Ingresado");
                            showMessageDialog(null, "Su operación fue realizada con éxito, el numero del certificado de garantia es: " + mensaje_numcertificado);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
        });

        JBCHT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagCHT = true;
                boolean checks = true;
                float totalmenostasacht = 0;
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
                        checks = false;
                    }
                }

                //Toma el String Fecha de Vencimiento desde el JText Field FDVCHP y lo transforma en un date
                String FDVCHT = "";
                FDVCHT = TFFDVCHT.getText();
                LocalDate FDVCHTdate = LocalDate.parse(FDVCHT);
                if (FDVCHT.isEmpty()) {
                    showMessageDialog(null, "El campo fecha de vencimiento es mandatorio y no puede estar vacio");
                    DatosCorrectosFlagCHT = false;
                    checks = false;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                if (verif.fechavalida(FDVCHT) == false) {
                    showMessageDialog(null, "La fecha ingresada es invalida");
                    DatosCorrectosFlagCHT = false;
                    checks = false;
                } else {
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = verif.fechavshoy(FDVCHTdate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "El cheque se encuentra vencido");
                        DatosCorrectosFlagCHT = false;
                        checks = false;
                    }
                    if (comparacionfecha == "Hoy") {
                        showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                        DatosCorrectosFlagCHT = false;
                        checks = false;
                    }
                }


                //Toma el String CUIT Socio desde el JText Field TFCDFCHT
                String CDSCHT = "";
                CDSCHT = JTFCSCHT.getText();
                if (CDSCHT.equalsIgnoreCase("")) {
                    showMessageDialog(null, "El campo CUIT del firmante es mandatorio y no puede estar vacio");
                    DatosCorrectosFlagCHT = false;
                    checks = false;
                }
                if (verif.CUITValido(CDSCHT) != true) {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagCHT = false;
                    checks = false;
                }

                //Toma el Entero Importe Total desde el JSpinner spinnerITCHT
                Object ITCHT;
                ITCHT = spinnerITCHT.getValue();
                int ITCHTint;
                ITCHTint = (Integer) ITCHT;
                if (ITCHTint <= 0) {
                    showMessageDialog(null, "El cheque no puede tener un valor menor o igual a 0");
                    DatosCorrectosFlagCHT = false;
                    checks = false;
                }

                //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
                Object TDDCHT;
                TDDCHT = spinnerTDDCHT.getValue();
                int TDDCHTint;
                TDDCHTint = (Integer) TDDCHT;
                if (TDDCHTint <= 0) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                    DatosCorrectosFlagCHT = false;
                    checks = false;
                }
                if (TDDCHTint >= 100) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento superior al 99%");
                    DatosCorrectosFlagCHT = false;
                    checks = false;
                }

                // Cuit Firmante
                String CUITfirmante = "";
                CUITfirmante = TFCDFCHP.getText();

                ArrayList<String> EmpresaComparteSocio = new ArrayList<>();
                EmpresaComparteSocio = verif.ListaCUITAC(CDSCHT);
                boolean fdrflag = false;
                if (DatosCorrectosFlagCHT == true) {
                    if (verif.essocioparticipe(CDSCHT) != true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio no es un socio participe pleno");
                        checks = false;
                    } else if (verif.lineacreditovigente(CDSCHT) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que la linea de credito se encuentra vencida");
                        checks = false;
                    } else if (verif.debefacturas(CDSCHT) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio adeuda facturas");
                        checks = false;
                    } else if (verif.check_deuda(CDSCHT) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio registra deudas");
                        checks = false;
                    } else if (!EmpresaComparteSocio.isEmpty()) {
                        if (verif.Computar5FDRAc(EmpresaComparteSocio, CDSCHT, ITCHTint) == true) {
                            showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que supera la suma de los riesgos vivos de los socios que comparten accionistas");
                            checks = false;
                            fdrflag = true;
                        }
                    } else if (verif.lineatope(CDSCHT, ITCHTint) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el importe total excede el tope de la linea de credito asignada");
                        checks = false;
                    } else if (verif.contragarantiassuficientes(CDSCHT, ITCHTint) == false) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al total de las contragarantias presentadas");
                        checks = false;
                    } else if (fdrflag != true) {
                        try {
                            if (verif.operacionvsfdr(ITCHTint) == false) {
                                showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al 5% del fondo de riesgo");
                                checks = false;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }

                if (checks == true) {
                    int mensaje_numcertificado = 0;
                    try {
                        totalmenostasacht = (float) ITCHTint - ((float) ITCHTint * ((float) TDDCHTint / 100));
                        mensaje_numcertificado = verif.crearOT1(FDVCHTdate, BECHT.toString(), NCCHTint, CDSCHT, TDDCHTint, CUITfirmante, "Cheque de terceros", totalmenostasacht, "Ingresado");
                        showMessageDialog(null, "Su operación fue realizada con éxito, el numero del certificado de garantia es: " + mensaje_numcertificado);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
    });

        // Pagare Bursatil
        JBPB.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            boolean DatosCorrectosFlagPB = true;
            boolean checks = true;
            float totalmenostasapb = 0;
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
                checks = false;
            } else {
                if (verif.esnumerico(NDPPB)) {
                    NDPPBint = Integer.parseInt(NDPPB);
                } else {
                    showMessageDialog(null, "El campo Numero de pagare solo admite numeros, no se admiten otros caracteres");
                    DatosCorrectosFlagPB = false;
                    checks = false;
                }
            }


            //Toma el String Fecha de Vencimiento desde el JText Field FDVCHP y lo transforma en un date
            String FDVPB = "";
            FDVPB = TFFDVPB.getText();
            LocalDate FDVPBdate = LocalDate.parse(FDVPB);
            if (FDVPB.isEmpty()) {
                showMessageDialog(null, "El campo fecha de vencimiento es mandatorio y no puede estar vacio");
                DatosCorrectosFlagPB = false;
                checks = false;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            if (verif.fechavalida(FDVPB) == false) {
                showMessageDialog(null, "La fecha ingresada es invalida");
                DatosCorrectosFlagPB = false;
                checks = false;
            } else {
                //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                // canje o vender un cheque ya vencido.
                String comparacionfecha = verif.fechavshoy(FDVPBdate);
                if (comparacionfecha == "Menor") {
                    showMessageDialog(null, "El cheque se encuentra vencido");
                    DatosCorrectosFlagPB = false;
                    checks = false;
                }
                if (comparacionfecha == "Hoy") {
                    showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                    DatosCorrectosFlagPB = false;
                    checks = false;
                }
            }

            //Toma el String CUIT Socio desde el JText Field TFCDFCHT
            String CDSPB = "";
            CDSPB = JTFCSPB.getText();
            if (CDSPB.equalsIgnoreCase("")) {
                showMessageDialog(null, "El campo CUIT del firmante es mandatorio y no puede estar vacio");
                DatosCorrectosFlagPB = false;
                checks = false;
            }
            if (verif.CUITValido(CDSPB) != true) {
                showMessageDialog(null, "El CUIT ingresado es invalido");
                DatosCorrectosFlagPB = false;
                checks = false;
            }

            //Toma el Entero Importe Total desde el JSpinner spinnerITPB
            Object ITPB;
            ITPB = spinnerITPB.getValue();
            int ITPBint;
            ITPBint = (Integer) ITPB;
            if (ITPBint <= 0) {
                showMessageDialog(null, "El pagare bursatil no puede tener un valor menor o igual a 0");
                DatosCorrectosFlagPB = false;
                checks = false;
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

            String CUITFirmante="";
            CUITFirmante = TFCDFPB.getText();

            ArrayList<String> EmpresaComparteSocio = new ArrayList<>();
            EmpresaComparteSocio = verif.ListaCUITAC(CDSPB);
            boolean fdrflag = false;
            if (DatosCorrectosFlagPB == true) {
                if (verif.essocioparticipe(CDSPB) != true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio no es un socio participe pleno");
                    checks = false;
                } else if (verif.lineacreditovigente(CDSPB) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que la linea de credito se encuentra vencida");
                    checks = false;
                } else if (verif.debefacturas(CDSPB) == true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio adeuda facturas");
                    checks = false;
                } else if (verif.check_deuda(CDSPB) == true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio registra deudas");
                    checks = false;
                } else if (!EmpresaComparteSocio.isEmpty()) {
                    if (verif.Computar5FDRAc(EmpresaComparteSocio, CDSPB, ITPBint) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que supera la suma de los riesgos vivos de los socios que comparten accionistas");
                        checks = false;
                        fdrflag = true;
                    }
                } else if (verif.lineatope(CDSPB, ITPBint) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el importe total excede el tope de la linea de credito asignada");
                    checks = false;
                } else if (verif.contragarantiassuficientes(CDSPB, ITPBint) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al total de las contragarantias presentadas");
                    checks = false;
                } else if (fdrflag != true) {
                    try {
                        if (verif.operacionvsfdr(ITPBint) == false) {
                            showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al 5% del fondo de riesgo");
                            checks = false;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
                if (checks == true) {
                    int mensaje_numcertificado = 0;
                    try {
                        totalmenostasapb = (float) ITPBint - ((float) ITPBint * ((float) TDDPBint / 100));
                        mensaje_numcertificado = verif.crearOT1(FDVPBdate, BEPB.toString(), NDPPBint, CDSPB, TDDPBint, CUITFirmante, "Pagare Bursatil", totalmenostasapb, "Ingresado");
                        showMessageDialog(null, "Su operación fue realizada con éxito, el numero del certificado de garantia es: " + mensaje_numcertificado);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
    });


    // El actionListener de Prestamos
        JBPST.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            boolean DatosCorrectosFlagPST = true;
            boolean checks = true;
            //Toma el objeto Banco Emisor desde el Combo Box comboBECHP
            Object BEPST = new Object();
            BEPST = comboBEPST.getSelectedItem();

            //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
            Object TDDPST;
            TDDPST = spinnerTDDPST.getValue();
            int TDDPSTint;
            TDDPSTint = (Integer) TDDPST;
            if (TDDPSTint <= 0) {
                DatosCorrectosFlagPST = false;
                checks = false;
                showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
            }

            //Toma el importe total
            Object ITPST;
            ITPST = spinnerITPST.getValue();
            int ITFloatPST;
            ITFloatPST = (Integer) ITPST;
            if (ITFloatPST <= 0) {
                DatosCorrectosFlagPST = false;
                checks = false;
                showMessageDialog(null, "El importe total del prestamo no puede ser igual o menor que 0");
            }

            //Toma el String Fecha de Vencimiento desde el JText Field FDVCHP y lo transforma en un date
            String FDVPST = "";
            FDVPST = TFFDAPST.getText();
            LocalDate FDVPSTdate = LocalDate.parse(FDVPST);
            if (FDVPST.isEmpty()) {
                showMessageDialog(null, "El campo fecha de vencimiento es mandatorio y no puede estar vacio");
                DatosCorrectosFlagPST = false;
                checks = false;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            if (verif.fechavalida(FDVPST) == false) {
                showMessageDialog(null, "La fecha ingresada es invalida");
                DatosCorrectosFlagPST = false;
                checks = false;
            } else {
                //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                // canje o vender un cheque ya vencido.
                String comparacionfecha = verif.fechavshoy(FDVPSTdate);
                if (comparacionfecha == "Menor") {
                    DatosCorrectosFlagPST = false;
                    checks = false;
                    showMessageDialog(null, "El prestamo tiene que ser a futuro");
                }
                if (comparacionfecha == "Hoy") {
                    DatosCorrectosFlagPST = false;
                    checks = false;
                    showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                }
            }

            //Toma el String CUIT Socio desde el JText Field TFCDFCHT
            String CSPST = "";
            CSPST = TFCSPST.getText();
            if (CSPST.equalsIgnoreCase("")) {
                showMessageDialog(null, "El campo CUIT del firmante es mandatorio y no puede estar vacio");
                DatosCorrectosFlagPST = false;
                checks = false;
            }
            if (verif.CUITValido(CSPST) != true) {
                showMessageDialog(null, "El CUIT ingresado es invalido");
                DatosCorrectosFlagPST = false;
                checks = false;
            }

            //Toma el objeto Sistema desde el Combo Box comboSistemaPST
            Object Sistema_PST = new Object();
            Sistema_PST = comboSistemaPST.getSelectedItem();

            //Toma el objeto Cantidad de cuotas desde el Combo Box comboSistemaPST
            Object cantidadCuotas = new Object();
            cantidadCuotas = comboBoxCDCPST.getSelectedItem();





            ArrayList<String> EmpresaComparteSocio = new ArrayList<>();
            EmpresaComparteSocio = verif.ListaCUITAC(CSPST);
            boolean fdrflag = false;
            if (DatosCorrectosFlagPST == true) {
                if (verif.essocioparticipe(CSPST) != true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio no es un socio participe pleno");
                    checks = false;
                } else if (verif.lineacreditovigente(CSPST) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que la linea de credito se encuentra vencida");
                    checks = false;
                } else if (verif.debefacturas(CSPST) == true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio adeuda facturas");
                    checks = false;
                } else if (verif.check_deuda(CSPST) == true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio registra deudas");
                    checks = false;
                } else if (!EmpresaComparteSocio.isEmpty()) {
                    if (verif.Computar5FDRAc(EmpresaComparteSocio, CSPST, ITFloatPST) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que supera la suma de los riesgos vivos de los socios que comparten accionistas");
                        checks = false;
                        fdrflag = true;
                    }
                } else if (verif.lineatope(CSPST, ITFloatPST) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el importe total excede el tope de la linea de credito asignada");
                    checks = false;
                } else if (verif.contragarantiassuficientes(CSPST, ITFloatPST) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al total de las contragarantias presentadas");
                    checks = false;
                } else if (fdrflag != true) {
                    try {
                        if (verif.operacionvsfdr(ITFloatPST) == false) {
                            showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al 5% del fondo de riesgo");
                            checks = false;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
                if (checks == true) {
                    int mensaje_numcertificado = 0;
                    try {
                        mensaje_numcertificado = verif.crearOT3(cantidadCuotas.toString(), BEPST.toString(), ITFloatPST, TDDPSTint, Sistema_PST.toString(), FDVPSTdate, CSPST, "Ingresado", "Prestamo");
                        showMessageDialog(null, "Su operación fue realizada con éxito, el numero del certificado de garantia es: " + mensaje_numcertificado);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
    });

        pnlTabPanelOps.addMouseListener(new

    MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e){
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
            int numerocertificado = 0;
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
                        if (numeroopNDOC == numeroopcdgNDOC) {
                            numerocertificado = Integer.parseInt(certificado.get("idcertificado").toString());
                            comboNDOC.addItem(numerocertificado);
                        }
                    }
                }
            }
        }
    });

        JBC.addActionListener(new

    ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            double montocomision = 0;
            int numerooperacion = Integer.parseInt(comboNDOC.getSelectedItem().toString());
            try {
                montocomision = verif.nuevacomision(numerooperacion);
                showMessageDialog(null, "El monto de la comision es: " + montocomision);
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
            int numerocertificado = 0;
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
                        if (numeroopNDOC == numeroopcdgNDOC) {
                            numerocertificado = Integer.parseInt(certificado.get("idcertificado").toString());
                            comboNDOC.addItem(numerocertificado);
                        }
                    }
                }
            }
        }
    });

    // Cuenta corriente
        JBCCC.addActionListener(new

    ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            boolean DatosCorrectosFlagCCC = true;
            boolean checks = true;
            //Toma el String Fecha de Vencimiento desde el JText Field FDVCHP y lo transforma en un date
            String FDVCCC = "";
            FDVCCC = TFFDVCCC.getText();
            LocalDate FDVCCCdate = LocalDate.parse(FDVCCC);
            if (FDVCCC.isEmpty()) {
                showMessageDialog(null, "El campo fecha de vencimiento es mandatorio y no puede estar vacio");
                DatosCorrectosFlagCCC = false;
                checks = false;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            if (verif.fechavalida(FDVCCC) == false) {
                showMessageDialog(null, "La fecha ingresada es invalida");
                DatosCorrectosFlagCCC = false;
                checks = false;
            } else {
                //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                // canje o vender un cheque ya vencido.
                String comparacionfecha = verif.fechavshoy(FDVCCCdate);
                if (comparacionfecha == "Menor") {
                    showMessageDialog(null, "El cheque se encuentra vencido");
                    DatosCorrectosFlagCCC = false;
                    checks = false;
                }
                if (comparacionfecha == "Hoy") {
                    showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                    DatosCorrectosFlagCCC = false;
                    checks = false;
                }
            }

            String CSCCC = "";
            CSCCC = TFCUIT.getText();
            if (CSCCC.equalsIgnoreCase("")) {
                showMessageDialog(null, "El campo CUIT del firmante es mandatorio y no puede estar vacio");
                DatosCorrectosFlagCCC = false;
                checks = false;
            }
            if (verif.CUITValido(CSCCC) != true) {
                showMessageDialog(null, "El CUIT ingresado es invalido");
                DatosCorrectosFlagCCC = false;
                checks = false;
            }

            //Toma el String Numero de Cheque desde el JText Field TFNCCHP y lo transforma en un entero
            String ITCCC;
            int ITCCCint;
            ITCCC = TFITCCC.getText();
            if (ITCCC.isEmpty()) {
                showMessageDialog(null, "El campo Empresa es mandatorio, por favor ingrese el dato solicitado");
                DatosCorrectosFlagCCC = false;
                checks = false;
            }
            Object SITCCC;
            SITCCC = spinnerITCCC.getValue();
            int SITCCCint;
            SITCCCint = (Integer) SITCCC;
            if (SITCCCint <= 0) {
                showMessageDialog(null, "El importe total debe ser mayor que 0");
                DatosCorrectosFlagCCC = false;
                checks = false;
            }

            String nombre = "";
            nombre = TFNombreTC.getText();

            double importetotal = 0;
            importetotal = Double.parseDouble(spinnerITCCC.getValue().toString());

            String FDV = "";
            FDV = FDVCCCdate.toString();


            ArrayList<String> EmpresaComparteSocio = new ArrayList<>();
            EmpresaComparteSocio = verif.ListaCUITAC(CSCCC);
            boolean fdrflag = false;
            if (DatosCorrectosFlagCCC == true) {
                if (verif.essocioparticipe(CSCCC) != true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio no es un socio participe pleno");
                    checks = false;
                } else if (verif.lineacreditovigente(CSCCC) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que la linea de credito se encuentra vencida");
                    checks = false;
                } else if (verif.debefacturas(CSCCC) == true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio adeuda facturas");
                    checks = false;
                } else if (verif.check_deuda(CSCCC) == true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio registra deudas");
                    checks = false;
                } else if (!EmpresaComparteSocio.isEmpty()) {
                    if (verif.Computar5FDRAc(EmpresaComparteSocio, CSCCC, importetotal) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que supera la suma de los riesgos vivos de los socios que comparten accionistas");
                        checks = false;
                        fdrflag = true;
                    }
                } else if (verif.lineatope(CSCCC, (float) importetotal) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el importe total excede el tope de la linea de credito asignada");
                    checks = false;
                } else if (verif.contragarantiassuficientes(CSCCC, (float) importetotal) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al total de las contragarantias presentadas");
                    checks = false;
                } else if (fdrflag != true) {
                    try {
                        if (verif.operacionvsfdr(importetotal) == false) {
                            showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al 5% del fondo de riesgo");
                            checks = false;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            if (checks == true) {
                int mensaje_numcertificado = 0;
                try {
                    mensaje_numcertificado = verif.crearOT2("",importetotal,FDV,CSCCC,0,"","Ingresado",0,"Cuenta Corriente",nombre);
                    showMessageDialog(null, "Su operación fue realizada con éxito, el numero del certificado de garantia es: " + mensaje_numcertificado);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

    });

    // Tarjeta de credito
        JBTC.addActionListener(new

    ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            boolean DatosCorrectosFlagTC = true;
            boolean checks = true;
            String NombreTC;
            int NombreTCint;
            NombreTC = TFNombreTC.getText();
            if (NombreTC.isEmpty()) {
                showMessageDialog(null, "Debe ingresar el nombre completo tal como figura en la tarjeta");
                DatosCorrectosFlagTC = false;
                checks = false;
            }


            String FVTC = "";
            FVTC = TFFVTC.getText();
            if (verif.fechavalidatarjeta(FVTC) == true) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                YearMonth yearMonth = YearMonth.parse(FVTC, formatter);
                String comparacionfecha = verif.fechavshoytarjeta(yearMonth);
                if (comparacionfecha == "Menor") {
                    showMessageDialog(null, "La tarjeta se encuentra vencida");
                    DatosCorrectosFlagTC = false;
                    checks = false;
                }

                FVTC = yearMonth.toString();

            } else {
                showMessageDialog(null, "La fecha de vencimiento ingresada no cumple con el formato solicitado");
                DatosCorrectosFlagTC = false;
                checks = false;
            }

            String CDSTC;
            int CDSTCint = 0;
            CDSTC = TFCDSTC.getText();
            if (CDSTC.isEmpty()) {
                showMessageDialog(null, "El código no puede quedar vacío");
                DatosCorrectosFlagTC = false;
                checks = false;
            }

            if (verif.esnumerico(CDSTC)) {
                if (CDSTC.length() != 3) {
                    showMessageDialog(null, "El código debe ser de 3 números");
                }
            }
            if (!verif.esnumerico(CDSTC) && !CDSTC.isEmpty()) {
                showMessageDialog(null, "El código debe ser numérico");
                DatosCorrectosFlagTC = false;
                checks = false;
            }
            String CUIT2 = "";
            CUIT2 = TFCUIT2.getText();
            if (verif.CUITValido(CUIT2)) {
            } else {
                showMessageDialog(null, "El CUIT ingresado es invalido");
                DatosCorrectosFlagTC = false;
                checks = false;
            }
            String NDTTC;
            int TFNDTTCint = 0;
            NDTTC = TFNDTTC.getText();
            if (NDTTC.contains("-") && NDTTC.length() > 18) {
                if (verif.tarjetavalida(NDTTC)) {
                } else {
                    showMessageDialog(null, "La tarjeta ingresada es inválida");
                    DatosCorrectosFlagTC = false;
                    checks = false;
                }
            } else {
                if (NDTTC.isEmpty()) {
                    showMessageDialog(null, "Ingrese el número de la tarjeta");
                } else {
                    showMessageDialog(null, "El número de tarjeta debe contener '-' y 16 números");
                }

            }

            double IT;
            IT = Double.parseDouble(ITTC.getValue().toString());

            String CUITSocioTC;
            CUITSocioTC = TFCUIT2.getText();


            ArrayList<String> EmpresaComparteSocio = new ArrayList<>();
            EmpresaComparteSocio = verif.ListaCUITAC(CUITSocioTC);
            boolean fdrflag = false;
            if (DatosCorrectosFlagTC == true) {
                if (verif.essocioparticipe(CUITSocioTC) != true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio no es un socio participe pleno");
                    checks = false;
                } else if (verif.lineacreditovigente(CUITSocioTC) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que la linea de credito se encuentra vencida");
                    checks = false;
                } else if (verif.debefacturas(CUITSocioTC) == true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio adeuda facturas");
                    checks = false;
                } else if (verif.check_deuda(CUITSocioTC) == true) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el socio registra deudas");
                    checks = false;
                } else if (!EmpresaComparteSocio.isEmpty()) {
                    if (verif.Computar5FDRAc(EmpresaComparteSocio, CUITSocioTC, IT) == true) {
                        showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que supera la suma de los riesgos vivos de los socios que comparten accionistas");
                        checks = false;
                        fdrflag = true;
                    }
                } else if (verif.lineatope(CUITSocioTC, (float) IT) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que el importe total excede el tope de la linea de credito asignada");
                    checks = false;
                } else if (verif.contragarantiassuficientes(CUITSocioTC, (float) IT) == false) {
                    showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al total de las contragarantias presentadas");
                    checks = false;
                } else if (fdrflag != true) {
                    try {
                        if (verif.operacionvsfdr(IT) == false) {
                            showMessageDialog(null, "La operacion solicitada no puede ser cursada ya que es mayor al 5% del fondo de riesgo");
                            checks = false;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            if (checks == true) {
                    int mensaje_numcertificado = 0;
                    try {
                        System.out.println("mando la tarjeta");
                        verif.crearOT2(TFITCCC.toString(), IT, FVTC, CUITSocioTC, TFNDTTCint, TFNombreTC.getText(), "Ingresado", CDSTCint, "Tarjeta de Credito", "-");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        }
    });
}
}






