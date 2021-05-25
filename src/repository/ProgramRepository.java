package repository;

import config.DatabaseConnection;
import models.*;
import java.sql.*;

public class ProgramRepository {
    public void addProgram(Program program) {
        String sql = "insert into program (id_program, name, number_years) " +
                "values (null, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, program.getName());
            statement.setInt(2, program.getNumberYears());
            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int idProgram = generatedKey.getInt(1);
                    program.setIdProgram(idProgram);
                } else {
                    throw new SQLException("Failed to get program id!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
