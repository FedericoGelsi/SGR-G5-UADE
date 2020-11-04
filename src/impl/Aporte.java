package impl;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Aporte implements api.Aporte {
    private int IDAporte;
    private Date fechaCreacion;
    private float montoAporte;
    private File documento;
    private int SocioAportante;

    /*======CONSTRUCTOR=======*/
    public Aporte(float montoAporte, File documento, int socioAportante) throws ParseException {
        this.montoAporte = montoAporte;
        this.documento = documento;
        this.fechaCreacion = new Date();
        //String sDate1="31/12/1998";
        //this.fechaCreacion=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        //System.out.println(sDate1+"\t"+this.fechaCreacion);
        SocioAportante = socioAportante;
    }

    /*======GETTERS=======*/
    @Override
    public int getIDAporte() {
        return IDAporte;
    }

    @Override
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public float getMontoAporte() {
        return montoAporte;
    }

    @Override
    public File getDocumento() {
        return documento;
    }

    @Override
    public int getSocioAportante() {
        return SocioAportante;
    }

    /*======CLASS FUNCTIONS=======*/
    @Override
    public boolean calcularVigencia(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date hoy = new Date();
            System.out.println("Current Date " + dateFormat.format(hoy));

            // Convert Date to Calendar
            Calendar c = Calendar.getInstance();
            c.setTime(this.fechaCreacion);

            // Perform addition/subtraction
            c.add(Calendar.YEAR, 2);

            // Convert calendar back to Date
            Date fecha2años = c.getTime();

            System.out.println("Updated Date " + dateFormat.format(fecha2años));

            // Comparacion
            if (hoy.compareTo(fecha2años)<0) {
                return true;
            }
            else{
                return false;
                }
        }


    }


