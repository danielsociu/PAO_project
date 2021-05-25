package repository;

import config.DatabaseConnection;
import models.School;

import java.sql.*;

public class SchoolRepository {

    public void addSchool(School school) {
        String sql = "insert into school values (null, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, school.getName());
            statement.executeUpdate();
            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int idSchool = generatedKey.getInt(1);
                    school.setIdSchool(idSchool);
                } else {
                    throw new SQLException("Failed to get school id!");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSchool(School school) {
        String sql = "update school set name = ? where id_schol = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, school.getName());
            statement.setInt(2, school.getIdSchool());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listSchools() {
        String sql = "select * from school";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            System.out.println("Schools available:");
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString("name");
                System.out.println("Id: " + id + ", name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSchool(String name) throws SQLException {
        String sql = "select * from school sc where sc.name = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int schoolId = result.getInt(1);
                String schoolName = result.getString("name");
                School school = School.getSchool();
                school.setIdSchool(schoolId);
                school.setName(schoolName);
                return schoolId;
            }
            throw new SQLException("School not found!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Cannot get statement!");
        }
    }

    public int getSchoolId(int idSchool) throws SQLException {
        String sql = "select * from school sc where sc.id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idSchool);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int schoolId = result.getInt(1);
                String schoolName = result.getString("name");
                School school = School.getSchool();
                school.setIdSchool(schoolId);
                school.setName(schoolName);
                return schoolId;
            }
            throw new SQLException("School not found!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Cannot get statement!");
        }
    }

}
