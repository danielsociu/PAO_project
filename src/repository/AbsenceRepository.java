package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceRepository {

    public void addAbsence(models.Class myClass, Absence absence) {
        String sqlAbsence = "insert into absence (id_absence, pid_student, pid_teacher, id_subject, id_class, date, motivated) " +
                "values (null, ?, ?, ?, ?, ?, ?)";
        try (
            PreparedStatement statementAbsence = DatabaseConnection.getConnection().prepareStatement(
                sqlAbsence,
                Statement.RETURN_GENERATED_KEYS
            )) {
            statementAbsence.setString(1, absence.getStudent().getPid());
            statementAbsence.setString(2, absence.getTeacher().getPid());
            statementAbsence.setInt(3, absence.getSubject().getIdSubject());
            statementAbsence.setInt(4, myClass.getIdClass());
            statementAbsence.setDate(5, new java.sql.Date(absence.getDate().getTime()));
            statementAbsence.setInt(6, absence.isMotivated() ? 1 : 0);
            statementAbsence.executeUpdate();

            try (ResultSet generatedKey = statementAbsence.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int idAbsence = generatedKey.getInt(1);
                    absence.setIdAbsence(idAbsence);
                } else {
                    throw new SQLException("Failed to get absence id!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAbsence(Absence absence) {
        String sql = "update absence set motivated = ? where id_absence = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, absence.isMotivated() ? 1 : 0);
            statement.setInt(2, absence.getIdAbsence());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAbsence(Absence absence) {
        String sql = "delete from absence where id_absence = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, absence.getIdAbsence());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Absence> getAbsences(
        models.Class myClass,
        List<Student> students,
        List<Subject> subjects,
        List<Teacher> teachers
    ) {
        String sql = "select * from absence where id_class = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, myClass.getIdClass());
            List<Absence> absences = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String pidStudent = rs.getString("pid_student");
                String pidTeacher = rs.getString("pid_teacher");
                int idSubject = rs.getInt("id_subject");
                Student myStudent = null;
                Teacher myTeacher = null;
                Subject mySubject = null;
                for (Student student: students) {
                    if (pidStudent.equals(student.getPid())) {
                        myStudent = student;
                        break;
                    }
                }
                for (Teacher teacher: teachers) {
                    if (pidTeacher.equals(teacher.getPid())) {
                        myTeacher = teacher;
                        break;
                    }
                }
                for (Subject subject: subjects) {
                    if (idSubject == subject.getIdSubject()) {
                        mySubject = subject;
                        break;
                    }
                }
                absences.add(
                        new Absence.Builder()
                            .withIdAbsence(rs.getInt("id_absence"))
                            .withStudent(myStudent)
                            .withSubject(mySubject)
                            .withTeacher(myTeacher)
                            .withDate(rs.getDate("date"))
                            .withMotivated(rs.getBoolean("motivated"))
                            .build()
                );
            }
            return absences;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
