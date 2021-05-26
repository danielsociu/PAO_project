package repository;

import config.DatabaseConnection;
import models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramRepository {
    public void addProgram(School school, Program program) {
        String sql = "insert into program (id_program, id_school, name, number_years) " +
                "values (null, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, school.getIdSchool());
            statement.setString(2, program.getName());
            statement.setInt(3, program.getNumberYears());
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

    public void removeProgram(School school, Program program) {
        String sql = "delete from program where id_program = ? and id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, program.getIdProgram());
            statement.setInt(2, school.getIdSchool());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Program> getPrograms(School school) {
        String sql = "select * from program where id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, school.getIdSchool());
            ResultSet rs = statement.executeQuery();
            ArrayList<Program> programs = new ArrayList<>();
            while (rs.next()) {
                programs.add(new Program(
                        rs.getInt("id_program"),
                        rs.getString("name"),
                        rs.getInt("number_years")
                ));
                System.out.println(programs.get(programs.size() -1));
            }
            return programs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
