package com.bisa.cam.datasources;

import com.bisa.cam.business.repositories.CuentaRepository;
import com.bisa.cam.domain.Cuenta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;

@Component
public class ConexionMysql implements CuentaRepository {

    private final Logger logger = LogManager.getLogger(getClass());

    @Value("${base-datos.mysql.user}")
    private String usuario;

    @Value("${base-datos.mysql.password}")
    private String password;

    @Value("${base-datos.mysql.urljdbc}")
    private String urljdbc;

    @Override
    public Cuenta consultaCuenta(Long nrocuenta) throws Exception {
        logger.info("user:{} pawd:{}", usuario, password);

        //String user = "root";
        //String pwd = "desa";
        String query = "select id, nroCuenta,tipoCuenta, moneda,idCliente,\n" +
                " saldo,fechaApertura,lugarApertura, estado \n" +
                " from dbcuentas.cuenta\n" +
                " where  nroCuenta = ?\n";
        logger.info("CONSULTANDO >>>>" +  " MYSQL:");

        DataSource dataSource = getDataSource(usuario, password, urljdbc);
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Boolean feriado = false;
        Cuenta cuenta = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(query);
            cs.setLong(1, nrocuenta);
            logger.info("CONSULTANDO >>>>" +  " SQL:" + cs);
            rs = cs.executeQuery();
            while (rs.next()) {
                logger.info("RESULTADO SQL>>>>" + rs.getString(1));
                cuenta = new Cuenta();
                cuenta.setId(rs.getLong("id"));
                cuenta.setEstado(rs.getString("estado"));
                cuenta.setNroCuenta(rs.getLong("nroCuenta"));
                cuenta.setTipoCuenta(rs.getString("tipoCuenta"));
                cuenta.setIdCliente(rs.getLong("idCliente"));
                cuenta.setSaldo(rs.getDouble("saldo"));
                cuenta.setMoneda(rs.getString("moneda"));
                cuenta.setFechaApertura(rs.getTimestamp("fechaApertura"));
                cuenta.setLugarApertura(rs.getString("lugarApertura"));
                logger.info("OBJETO CUENTA>>>>" + cuenta);
            }

        } catch (SQLException e) {
            logger.info("<SQLException>>>>>", e);
            throw new Exception(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return cuenta;
    }

    @Override
    public Boolean registrarCuenta(Cuenta cuenta) throws Exception {
        //String user = "root";
        //String pwd = "desa";
        String query = "INSERT INTO dbcuentas.cuenta\n" +
                "( nroCuenta,  tipoCuenta,  moneda,  idCliente,\n" +
                "  saldo,  fechaApertura,  lugarApertura,  estado)\n" +
                " VALUES ( ?, ?, ?,  ?, 0, current_timestamp, 'LA PAZ', 'ACT')";

        logger.info("CONSULTANDO >>>>" +  " MYSQL:");

        DataSource dataSource = getDataSource(usuario, password, urljdbc);
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Boolean registrado = false;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(query);
            cs.setLong(1, cuenta.getNroCuenta());
            cs.setString(2, cuenta.getTipoCuenta());
            cs.setString(3, cuenta.getMoneda());
            cs.setLong(4, cuenta.getIdCliente());
            logger.info("CONSULTANDO >>>>" +  " SQL:" + cs);

            int res = cs.executeUpdate();
            if(res>0) registrado = true;

        } catch (SQLException e) {
            logger.info("<SQLException>>>>>", e);
            throw new Exception(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return registrado;
    }

    public static DataSource getDataSource(final String user, final String pwd, final String connectionUrl) {

        LogManager.getLogger("Conecatando a BD con usuario: {"+user+"} passwoed: {"+pwd+"}");
        LogManager.getLogger().info("Obteniendo DataSource...");

        PoolConfiguration config = new PoolProperties();
        config.setMaxActive(100);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        //final String connectionUrl = "jdbc:mysql://localhost:3306/dbcuentas";
        //final String connectionUrl = "jdbc:mysql://172.19.0.2:3306/dbcuentas";
        LogManager.getLogger().info("URL={}", connectionUrl);
        config.setUrl(connectionUrl);
        config.setUsername(user);
        config.setPassword(pwd);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(config);
        return dataSource;
    }


}
