package repository;

import config.DatabaseConnection;
import models.Program;
import models.Subject;

import java.sql.*;

public class SubjectRepository {
    public void addSubject(Subject subject) {
        String sql = "insert into subject (id_subject, name, domain) " +
                "values (null, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, subject.getName());
            statement.setString(2, subject.getDomain());
            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int idSubject = generatedKey.getInt(1);
                    subject.setIdSubject(idSubject);
                } else {
                    throw new SQLException("Failed to get subject id!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSubject(Subject subject) {
        String sql = "delete from subject where id_subject = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, subject.getIdSubject());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
