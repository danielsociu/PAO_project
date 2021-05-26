package repository;

import config.DatabaseConnection;
import models.School;
import models.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {
    public void addSubject(School school, Subject subject) {
        String sql = "insert into subject (id_subject, id_school, name, domain) " +
                "values (null, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, school.getIdSchool());
            statement.setString(2, subject.getName());
            statement.setString(3, subject.getDomain());
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

    public void removeSubject(School school, Subject subject) {
        String sql = "delete from subject where id_subject = ? and id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, subject.getIdSubject());
            statement.setInt(2, school.getIdSchool());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Subject> getSubjects(School school) {
        String sql = "select * from subject where id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, school.getIdSchool());
            ResultSet rs = statement.executeQuery();
            ArrayList<Subject> subjects = new ArrayList<>();
            while (rs.next()) {
                subjects.add(new Subject(
                        rs.getInt("id_subject"),
                        rs.getString("name"),
                        rs.getString("domain")
                ));
                System.out.println(subjects.get(subjects.size() -1));
            }
            return subjects;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
