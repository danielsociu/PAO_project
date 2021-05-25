package services;

import models.*;
import repository.*;

import java.lang.Class;
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
        SchoolRepository schoolRepository = new SchoolRepository();
        StudentRepository studentRepository = new StudentRepository();
        SubjectRepository subjectRepository = new SubjectRepository();
        TeacherRepository teacherRepository = new TeacherRepository();

        List<Subject> subjects = subjectRepository.getSubjects(school);

    }

}
