package repository;

import config.DatabaseConnection;
import models.School;

import java.sql.*;

public class SchoolRepository {

    public void addSchool(School school) {
        String sql = "insert into school values (null, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, school.getName());
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

    public int getSchool(String name) throws Exception {
        String sql = "select * from school sc where sc = ?";
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
            throw new Exception("School not found!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Cannot get statement!");
        }
    }
}
