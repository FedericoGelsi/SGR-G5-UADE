package impl;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static javax.swing.JOptionPane.showMessageDialog;

public class Verificaciones implements api.Verificaciones {
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
    public boolean esnumerico(String datos){
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

    public boolean lineacreditovigente(String CUIT){
        return true;
    }
}
