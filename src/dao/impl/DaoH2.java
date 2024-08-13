package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Dentista;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoH2 implements IDao<Dentista> {
    private static final Logger logger = Logger.getLogger(DaoH2.class);

    @Override
    public Dentista guardar(Dentista dentista) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = H2Connection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO dentistas (matricula, nombre, apellido) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, dentista.getMatricula());
            preparedStatement.setString(2, dentista.getNombre());
            preparedStatement.setString(3, dentista.getApellido());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                dentista.setMatricula(keys.getInt(1));
            }

            logger.info("Dentista guardado: " + dentista);
        } catch (SQLException e) {
            logger.error("Error al guardar dentista: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.error("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return dentista;
    }

    @Override
    public List<Dentista> listarTodos() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Dentista> dentistas = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM dentistas");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Dentista dentista = new Dentista();
                dentista.setMatricula(resultSet.getInt("matricula"));
                dentista.setNombre(resultSet.getString("nombre"));
                dentista.setApellido(resultSet.getString("apellido"));
                dentistas.add(dentista);
            }

            logger.info("Dentistas listados: " + dentistas.size());
        } catch (SQLException e) {
            logger.error("Error al listar dentistas: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.error("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return dentistas;
    }
    }
}
