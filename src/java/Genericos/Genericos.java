
package Genericos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Genericos {
  
   public static DateFormat formatterTime=  new SimpleDateFormat("HH:mm");
   private static final SimpleDateFormat formatoddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat formatoyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
 
    
    public static Time getDateTimeSql(String dateTime) {
       try {
           long ms = formatterTime.parse(dateTime).getTime();
           return new Time(ms);
       } catch (ParseException ex) {
           Logger.getLogger(Genericos.class.getName()).log(Level.SEVERE, null, ex);
       }
     return null;
    }
    
    

    public static String getFechaServidor() {
        ConexionDB.getInstancia();
        ResultSet rs;
        PreparedStatement ps;
        try {
            ps = ConexionDB.getRutaConexion().prepareStatement("select to_char(current_date,'DD/MM/YYYY') as fecha");
            rs = ps.executeQuery();
            rs.next();
            return rs.getString("fecha");
        } catch (Throwable e) {
            return "fecha invalida";
        }
    }

   

    public static String retornarFechaddMMyyyy(Date fechaRecuperada) {
        return formatoddMMyyyy.format(fechaRecuperada);
    }

    //retornar Fecha como String
    public static String retornarFechayyyyMMdd(String fecha) {
        Date fechaLocal = null;
        try {
            fechaLocal =  formatoddMMyyyy.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(Genericos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formatoyyyyMMdd.format(fechaLocal);
    }

    //retornar Fecha como Date
    public static java.sql.Date retornarFecha(String fechaRecibida) {
       // SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
       // SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");

        String strFecha = fechaRecibida;
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return fechaUtilTosql(fecha);
    }

     private static java.sql.Date fechaUtilTosql(java.util.Date utilDate) {
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }
    
}
