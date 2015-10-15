package co.gov.mme.oare.ws.dao;

import java.sql.*;

import co.gov.mme.oare.ws.connection.Conexion;
import co.gov.mme.oare.ws.entities.RegistroVenta;

public class Query {

    private Connection conn;
    public static final int MAX_CONSULTAS_DIARIAS = 800;

    public boolean consultarSiExcedeNumeroDeConsultasDiarias(String idVendedor) throws Exception{

        int count = 0;
        int index = 0;
        conn = Conexion.getConnection();
        String sql = "SELECT COUNT(*) c FROM subsidios.auditoria_servicios " +
                "WHERE fecha > CURRENT_DATE AND parametros LIKE '%idVendedor={?}%'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(++index, idVendedor);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            count = rs.getInt("c");
        }
        return count > MAX_CONSULTAS_DIARIAS;
    }

    public boolean consultarSiEsBeneficiario(long idUsuario) throws Exception{

        conn = Conexion.getConnection();
        String sql = "SELECT documento FROM subsidios.beneficiario WHERE documento = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }


    public String consultarSubsidio(long idUsuario, int capacidadCilindro, double valorCilindro, String ip, String idVendedor) throws Exception{

        conn = Conexion.getConnection();
        String sql = "SELECT subsidio from subsidios.calcular_subsidio(?,?,?,?,?) AS " +
                "(hogar text, precio_ref numeric, consumo_subsistencia numeric, " +
                "porcentaje_subsidiado numeric, saldo numeric, num_dias numeric, " +
                "precio_unitario numeric, pu numeric, subsidio_anticipado numeric, " +
                "saldo_anterior numeric, subsidio numeric, nuevo_saldo numeric)";
        int index = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(++index, idUsuario);
        ps.setInt(++index, capacidadCilindro);
        ps.setDouble(++index, valorCilindro);
        ps.setString(++index, ip);
        ps.setString(++index, idVendedor);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getString("subsidio");
        }
        return null;
    }

    public String validarSesion(String idEmpresa, String password) throws Exception{

        conn = Conexion.getConnection();
        String sql = "select * from subsidios.validar_sesion(?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        int index = 0;
        ps.setString(++index, idEmpresa);
        ps.setString(++index, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            return rs.getString(1);
        }
        else return null;
    }


    public boolean consultarSiTieneRegistroEnProceso(long idUsuario) throws Exception{

        conn = Conexion.getConnection();
        String sql = "SELECT * FROM subsidios.transacciones WHERE documento = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, idUsuario);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }


    public RegistroVenta registrarVenta(String ip, String idEmpresa, String idVendedor, long idUsuario, int capCilindro,
                                        double valorCilindro, String nifCilindro, double latitud, double longitud, long factura){


        String sql = "SELECT id, subsidio, valorTotal FROM subsidios.registrar_venta(?,?,?,?,?,?,?,?,?,?) " +
                " as (id integer, subsidio double precision, valorTotal double precision)";
        int index = 0;
        conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(++index, idEmpresa);
            ps.setLong(++index, idUsuario);
            ps.setInt(++index, capCilindro);
            ps.setDouble(++index, valorCilindro);
            ps.setString(++index, nifCilindro);
            ps.setString(++index, Double.toString(latitud).replace(",", "."));
            ps.setString(++index, Double.toString(longitud).replace(",", "."));
            ps.setLong(++index, factura);
            ps.setString(++index, idVendedor);
            ps.setString(++index, ip);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                RegistroVenta regVenta = new RegistroVenta(rs.getInt(1), rs.getDouble(2), rs.getDouble(3));
                return regVenta;
            }
        }catch(SQLException ex){

        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public void borrarTransacciones(long idUsuario) throws Exception{

        conn = Conexion.getConnection();
        String sql = "DELETE FROM subsidios.transacciones WHERE documento = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, idUsuario);
        ps.executeUpdate();

    }


    public boolean verificarPermisos(String idEmpresa, String idVendedor){

        try {
            conn = Conexion.getConnection();
            String sql = "SELECT subsidios.verificar_permisos(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idEmpresa.toUpperCase());
            ps.setString(2, idVendedor);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1).equals(idVendedor);
            }

        }catch(SQLException ex){

        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}