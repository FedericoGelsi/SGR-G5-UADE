package impl;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import api.Desembolso;
import api.OPTipo2;
import api.Socio_Participe;
import org.json.simple.JSONArray;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import api.API_JSONHandler;
import impl.JSONHandler;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

public class Verificaciones implements api.Verificaciones {

    private String filename = "./src/resources/socios.json";
    private API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);

    private String filenamefact = "./src/resources/operacioncontroller.json";
    private JSONObject jsonObjectOPC = (JSONObject) file.readJson(filenamefact);

    private String filenamefdr = "./src/resources/FondodeRiesgo.json";
    private JSONObject jsonObjectfdr = (JSONObject) file.readJson(filenamefdr);

    public Verificaciones() throws Exception {
    }

    //Compara una fecha entregada por parametro contra la fecha actual y devuelve Menor si la fecha ingresada es en el
    // pasado, Mayor si la fecha ingresada es en el futuro o Hoy si la fecha ingresada es la actual.
    @Override
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

    public String fechavshoytarjeta(YearMonth fecha) {
        LocalDate hoy = LocalDate.now(); //2020-09-13 --> 09/2
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        //LocalDate localDate = LocalDate.parse(hoy, formatter);
        Month MesHoy = hoy.getMonth();
        int AñoHoy = hoy.getYear();

        Month Mes = fecha.getMonth();
        int Año = fecha.getYear();

        if (Año < AñoHoy) {
            return "Menor";
        }
        if (Año == AñoHoy && Mes.compareTo(MesHoy) < 0) {
            return "Menor";
        } else {
            return "Mayor";
        }
    }

    public boolean tarjetavalida(String tarjeta) {
        String[] tarjetaseparada = tarjeta.split("-");
        boolean tarjetaValidaFlag = true;
        for (int i = 0; i < 4; i++) {
            if (tarjetaseparada[i].length() != 4 || esnumerico(tarjetaseparada[i]) != true) {
                tarjetaValidaFlag = false;
            }
        }
        return tarjetaValidaFlag;
    }

    //Chequea que el formato de CUIT ingresado sea valido y que los datos ingresads sean numericos
    @Override
    public boolean CUITValido(String CUIT) {
        String[] cuitseparado = CUIT.split("-");
        boolean CUITValidoflag = true;
        if ( Arrays.stream(cuitseparado).count() == 3) {
            if (cuitseparado[0].length() != 2 ||  esnumerico(cuitseparado[0]) != true) {
                CUITValidoflag = false;
            }
            if (cuitseparado[1].length() != 8 ||  esnumerico(cuitseparado[1]) != true) {
                CUITValidoflag = false;
            }
            if (cuitseparado[2].length() != 1 || esnumerico(cuitseparado[2]) != true) {
                CUITValidoflag = false;
            }
        }else {
            CUITValidoflag = false;
        }
        return CUITValidoflag;
    }


    //Chequea que un String este compuesto unicamente por numeros
    @Override
    public boolean esnumerico(String datos) {
        String regex = "[0-9]+";
        boolean numerico = datos.matches(regex);
        return numerico;
    }

    //Chequea el formato de fecha en el String recibido y que los datos ingresados sean numericos
    @Override
    public boolean fechavalida(String fechacheck){
        DateTimeFormatter formato = DateTimeFormatter.ISO_LOCAL_DATE;
        try{
            LocalDate.parse(fechacheck, formato);
        }catch(DateTimeParseException excep){
            showMessageDialog(null, "Ingrese una fecha válida.\nEl formato debe ser YYYY-MM-DD");
            return false;
        }
        return true;
    }

    public int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }


    public boolean fechavalidatarjeta (String fechacheck){
        String[] fechaseparada = fechacheck.split("/");
        boolean fechavalidaFlag = true;
        if (fechaseparada[0].length() != 2 || esnumerico(fechaseparada[0]) != true) {
            fechavalidaFlag = false;
        }
        if (fechaseparada[1].length() != 4 || esnumerico(fechaseparada[1]) != true) {
            fechavalidaFlag = false;
        }
        return fechavalidaFlag;
    }
    }




