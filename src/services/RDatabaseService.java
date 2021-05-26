package services;

import models.*;
import repository.*;

import java.util.List;

public class RDatabaseService {
    private static RDatabaseService rDatabaseService;

    public static RDatabaseService GetRDatabaseService() {
        if (rDatabaseService == null) {
            rDatabaseService = new RDatabaseService();
        }
        return rDatabaseService;
    }

    public void getAllData(School school) {
        AbsenceRepository absenceRepository = new AbsenceRepository();
        ClassRepository classRepository = new ClassRepository();
        GradeRepository gradeRepository = new GradeRepository();
        ProgramRepository programRepository = new ProgramRepository();
        StudentRepository studentRepository = new StudentRepository();
        SubjectRepository subjectRepository = new SubjectRepository();
        TeacherRepository teacherRepository = new TeacherRepository();

        List<Subject> subjects = subjectRepository.getSubjects(school);
        List<Program> programs = programRepository.getPrograms(school);
        List<models.Class> classes = classRepository.getClasses(school, programs);
        List<Student> students = studentRepository.getStudents(school, classes);
        List<Teacher> teachers = teacherRepository.getTeachers(school);
        for (Teacher teacher: teachers) {
            teacherRepository.getTeacherClasses(teacher, classes, subjects);
        }
        for (models.Class myClass: classes) {
            myClass.getCatalogue().setAbsences(
                absenceRepository.getAbsences(
                    myClass,
                    students,
                    subjects,
                    teachers
            ));
            myClass.getCatalogue().setGrades(
                gradeRepository.getGrades(
                        myClass,
                        students,
                        subjects,
                        teachers
            ));
        }
        school.setClasses(classes);
        school.setStudents(students);
        school.setTeachers(teachers);
        school.setPrograms(programs);
        school.setSubjects(subjects);
    }
}
