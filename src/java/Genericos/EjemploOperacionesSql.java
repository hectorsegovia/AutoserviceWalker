package Genericos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EjemploOperacionesSql {

    private ResultSet rs;
    private PreparedStatement ps;
    private String query;

    public EjemploOperacionesSql() {
        ConexionDB.getInstancia();
        Insertar();
    }

    private void Insertar() {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO cargo(descrip) VALUES (?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, "Cliente");
            if (ps.executeUpdate() > 0) {
                //commit
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
            } else {
                //rollback
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            }

        } catch (SQLException ex) {
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            Logger.getLogger(EjemploOperacionesSql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void Modificar() {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = " UPDATE cargo SET descrip=? WHERE cod_cargo=?; ";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, "Admin");
            ps.setInt(2, 1);
            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            }
        } catch (SQLException ex) {
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            Logger.getLogger(EjemploOperacionesSql.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void Eliminar() {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "DELETE FROM cargo WHERE cod_cargo=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, 2);
            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            }
        } catch (SQLException ex) {
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            Logger.getLogger(EjemploOperacionesSql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void ConsultarTodos() {
        try {
            query = "SELECT cod_cargo, descrip FROM cargo ORDER BY cod_cargo";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("codigo" + rs.getInt("cod_cargo"));
                System.out.println("Descripcion " + rs.getString("descrip"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(EjemploOperacionesSql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void ConsultarUnico() {
        try {
            query = "SELECT cod_cargo, descrip FROM cargo WHERE cod_cargo=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, 1);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Codigo " + rs.getInt("cod_cargo"));
                System.out.println("Descripcion " + rs.getString("descrip"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(EjemploOperacionesSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new EjemploOperacionesSql();
    }

}
