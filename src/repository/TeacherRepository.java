package repository;

import config.DatabaseConnection;
import models.*;
import models.Class;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TeacherRepository {

    public void addTeacher(School school, Teacher teacher) {
        String sql = "insert into teacher (pid_teacher, id_school, first_name, last_name, birth_date) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, teacher.getPid());
            statement.setInt(2, school.getIdSchool());
            statement.setString(3, teacher.getFirstName());
            statement.setString(4, teacher.getLastName());
            statement.setDate(5, new java.sql.Date(teacher.getBirthDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTeacher(School school, Teacher teacher) {
        String sql = "delete from teacher where pid_teacher = ? and id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, teacher.getPid());
            statement.setInt(2, school.getIdSchool());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTeacherClasses(Teacher teacher) {
        String sqlQuery = "select * from teacher_classes where pid_teacher = ?";
        String sqlAdd = "insert into teacher_classes (pid_teacher, id_class) " +
                "values (?, ?)";
        try (
            PreparedStatement statementQuery = DatabaseConnection.getConnection().prepareStatement(
                sqlQuery,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            PreparedStatement statementAdd = DatabaseConnection.getConnection().prepareStatement(sqlAdd)
        ) {
            statementQuery.setString(1, teacher.getPid());
            ResultSet existentClasses = statementQuery.executeQuery();
            for (models.Class myClass: teacher.getClasses()) {
                boolean ok = true;
                while (existentClasses.next()) {
                    if (existentClasses.getInt("id_class") == myClass.getIdClass()) {
                        existentClasses.beforeFirst();
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    statementAdd.setString(1, teacher.getPid());
                    statementAdd.setInt(2, myClass.getIdClass());
                    statementAdd.addBatch();
                }
            }
            statementAdd.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void addTeacherClass(Teacher teacher, models.Class myClass) {
//        String sql = "insert into teacher_classes (pid_teacher, id_class) " +
//                "values (?, ?)";
//        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
//            statement.setString(1, teacher.getPid());
//            statement.setInt(2, myClass.getIdClass());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public List<Teacher> getTeachers(School school) {
        String sql = "select * from teacher where id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, school.getIdSchool());
            ResultSet rs = statement.executeQuery();
            ArrayList<Teacher> teachers = new ArrayList<>();
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date"),
                        rs.getString("pid_teacher")
                ));
                System.out.println(teachers.get(teachers.size() -1));
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getTeacherClasses(Teacher teacher, List<models.Class> classes, List<Subject> subjects) {
        String sqlQuery = "select * from class_subjects where pid_teacher = ?";
        try (PreparedStatement statementQuery = DatabaseConnection.getConnection().prepareStatement(
                sqlQuery
        )) {
            statementQuery.setString(1, teacher.getPid());
            ResultSet existentClasses = statementQuery.executeQuery();
            while (existentClasses.next()) {
                int idClass = existentClasses.getInt("id_class");
                int idSubject = existentClasses.getInt("id_subject");
                models.Class foundClass = null;
                for (models.Class myClass: classes) {
                    if (myClass.getIdClass() == idClass) {
                        Set<Class> teacherClasses = teacher.getClasses();
                        foundClass = myClass;
                        teacherClasses.add(myClass);
                        break;
                    }
                }
                if (foundClass != null) {
                    for (Subject subject: subjects) {
                        if (subject.getIdSubject() == idSubject) {
                            HashMap<Subject, Teacher> classSubjects = foundClass.getSubjects();
                            classSubjects.put(subject, teacher);
                            foundClass.setSubjects(classSubjects);
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
