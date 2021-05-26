package repository;

import config.DatabaseConnection;
import models.*;
import models.Class;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassRepository {
    public void addClass(School school, models.Class myClass) {
        String sql = "insert into class (id_class, year, year_period, letter, id_program, id_school) " +
                "values (null, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, myClass.getYear());
            statement.setString(2, myClass.getYearPeriod());
            statement.setString(3, myClass.getLetter());
            statement.setInt(4, myClass.getProgram().getIdProgram());
            statement.setInt(5, school.getIdSchool());
            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int idClass = generatedKey.getInt(1);
                    myClass.setIdClass(idClass);
                } else {
                    throw new SQLException("Failed to get class id!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeClass(School school, models.Class myClass) {
        String sql = "delete from class where id_class = ? and id_school";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, myClass.getIdClass());
            statement.setInt(2, school.getIdSchool());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClass(models.Class myClass) {
        String sql = "update class " +
                "set year = ?, year_period = ?, letter = ?, id_program = ? " +
                "where id_class = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, myClass.getYear());
            statement.setString(2, myClass.getYearPeriod());
            statement.setString(3, myClass.getLetter());
            statement.setInt(4, myClass.getProgram().getIdProgram());
            statement.setInt(5, myClass.getIdClass());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubject(models.Class myClass, Subject subject, Teacher teacher) {
        String sql = "insert into class_subjects (id_class, id_subject, pid_teacher) " +
                "values (?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, myClass.getIdClass());
            statement.setInt(2, subject.getIdSubject());
            statement.setString(3, teacher.getPid());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeSubject(models.Class myClass, Subject subject) {
        String sql = "delete from class_subjects where id_class = ? and id_subject = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, myClass.getIdClass());
            statement.setInt(2, subject.getIdSubject());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<models.Class> getClasses(School school, List<Program> programs) {
        String sql = "select * from class where id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, school.getIdSchool());
            ResultSet rs = statement.executeQuery();
            ArrayList<models.Class> classes = new ArrayList<>();
            while (rs.next()) {
                int idProgram = rs.getInt("id_program");
                Program myProgram = null;
                for (Program program: programs) {
                    if (program.getIdProgram() == idProgram) {
                        myProgram = program;
                        break;
                    }
                }
                classes.add(new Class.Builder()
                        .withIdClass(rs.getInt("id_class"))
                        .withYear(rs.getString("year"))
                        .withYearPeriod(rs.getString("year_period"))
                        .withLetter(rs.getString("letter"))
                        .withProgram(myProgram)
                        .build()
                );
                System.out.println(classes.get(classes.size() -1));
                System.out.println(myProgram);
            }
            return classes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
