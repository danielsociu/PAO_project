package repository;

import config.DatabaseConnection;
import models.Program;
import models.School;
import models.Student;
import models.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Subject> getSubjects(School school) {
        String sql = "select s.* from subject s, class_subjects cs, class c " +
                "where s.id_subject = cs.id_subject and c.id_class = cs.id_class and c.id_school = ?";
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
