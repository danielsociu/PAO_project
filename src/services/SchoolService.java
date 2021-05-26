package services;

import models.*;
import models.Class;
import repository.*;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Date;

public class SchoolService {
    private static SchoolService schoolService;
    private RWService rwService;

    private SchoolService () {
        rwService = RWService.getRwService();
    }

    public static SchoolService getSchoolService() {
        if (schoolService == null) {
            schoolService = new SchoolService();
        }
        return schoolService;
    }

    public void addClass(Class schoolClass) {
        ClassRepository classRepository = new ClassRepository();
        School school = School.getSchool();
        List<Class> classes = school.getClasses();
        classes.add(schoolClass);
        school.setClasses(classes);

        String message = "Class " + schoolClass + " has been been added";
        rwService.addClassToFile(schoolClass);
        rwService.logger("AddClass - " + new Date().toString());
        classRepository.addClass(school, schoolClass);
        System.out.println(message);
    }

    public void addClassInteractive(Scanner in) {
        School school = School.getSchool();
        System.out.println("Enter the year period yyyy-yyyy:");
        String yearPeriod = in.nextLine();
        System.out.println("Enter the year");
        String year = in.nextLine();
        System.out.println("Enter the letter");
        String letter = in.nextLine();
        List<Program> programs = school.getPrograms();
        if (programs.size() == 0) {
            System.out.println("You need to first insert a program!");
            System.out.println("Enter the program name:");
            String programName = in.nextLine();
            System.out.println("Enter the program duration (years):");
            int duration = Integer.parseInt(in.nextLine());
            addProgram(new Program(
                    programName,
                    duration
            ));
            programs = school.getPrograms();
        }
        printList(programs);
        System.out.println("Enter the program digit:");
        int program = Integer.parseInt(in.nextLine());

        addClass(
                new Class.Builder()
                        .withYearPeriod(yearPeriod)
                        .withYear(year)
                        .withLetter(letter)
                        .withProgram(programs.get(program))
                        .build()
        );
    }

    public Class removeClass(int position) {
        ClassRepository classRepository = new ClassRepository();
        School school = School.getSchool();
        List<Class> classes = school.getClasses();
        Class deleted = classes.remove(position);
        school.setClasses(classes);
        String message = "Class " + deleted + " has been been deleted";
        System.out.println(message);
        rwService.logger("DeleteClass - " + new Date().toString());
        classRepository.removeClass(school, deleted);
        return deleted;
    }

    public void addStudent(Student student) {
        StudentRepository studentRepository = new StudentRepository();
        School school = School.getSchool();
        List<Student> students = school.getStudents();
        students.add(student);
        school.setStudents(students);
        String message = "Student " + student + " has been been added";
        rwService.addClassToFile(student);
        rwService.logger("AddStudent - " + new Date().toString());
        studentRepository.addStudent(school, student);
        System.out.println(message);
    }

    public Student removeStudent(int position) {
        StudentRepository studentRepository = new StudentRepository();
        School school = School.getSchool();
        List<Student> students = school.getStudents();
        Student deleted = students.remove(position);
        school.setStudents(students);
        String message = "Student " + deleted + " has been been deleted";
        System.out.println(message);
        rwService.logger("DeleteStudent - " + new Date().toString());
        studentRepository.removeStudent(school, deleted);
        return deleted;
    }

    public void addTeacher(Teacher teacher) {
        TeacherRepository teacherRepository = new TeacherRepository();
        School school = School.getSchool();
        List<Teacher> teachers = school.getTeachers();
        teachers.add(teacher);
        school.setTeachers(teachers);
        String message = "Teacher " + teacher + " has been been added";
        rwService.addClassToFile(teacher);
        rwService.logger("AddTeacher - " + new Date().toString());
        teacherRepository.addTeacher(school, teacher);
        System.out.println(message);
    }

    public Teacher removeTeacher(int position) {
        TeacherRepository teacherRepository = new TeacherRepository();
        School school = School.getSchool();
        List<Teacher> teachers = school.getTeachers();
        Teacher deleted = teachers.remove(position);
        school.setTeachers(teachers);
        String message = "Teacher " + deleted + " has been been deleted";
        System.out.println(message);
        rwService.logger("DeleteTeacher - " + new Date().toString());
        teacherRepository.removeTeacher(school, deleted);
        return deleted;
    }

    public void addProgram(Program program) {
        ProgramRepository programRepository = new ProgramRepository();
        School school = School.getSchool();
        List<Program> programs = school.getPrograms();
        programs.add(program);
        school.setPrograms(programs);
        String message = "Program " + program + " has been been added";
        rwService.addClassToFile(program);
        rwService.logger("AddProgram - " + new Date().toString());
        programRepository.addProgram(school, program);
        System.out.println(message);
    }

    public Program removeProgram(int position) {
        ProgramRepository programRepository = new ProgramRepository();
        School school = School.getSchool();
        List<Program> programs = school.getPrograms();
        Program deleted = programs.remove(position);
        school.setPrograms(programs);
        String message = "Program " + deleted + " has been been deleted";
        System.out.println(message);
        rwService.logger("DeleteProgram - " + new Date().toString());
        programRepository.removeProgram(school, deleted);
        return deleted;
    }

    public void addSubject(Subject subject) {
        SubjectRepository subjectRepository = new SubjectRepository();
        School school = School.getSchool();
        List<Subject> subjects = school.getSubjects();
        subjects.add(subject);
        school.setSubjects(subjects);
        String message = "Subject " + subject + " has been been added";
        rwService.addClassToFile(subject);
        rwService.logger("AddSubject - " + new Date().toString());
        subjectRepository.addSubject(school, subject);
        System.out.println(message);
    }

    public Subject removeSubject(int position) {
        SubjectRepository subjectRepository = new SubjectRepository();
        School school = School.getSchool();
        List<Subject> subjects = school.getSubjects();
        Subject deleted = subjects.remove(position);
        school.setSubjects(subjects);
        String message = "Subject " + deleted + " has been been deleted";
        System.out.println(message);
        rwService.logger("DeleteSubject - " + new Date().toString());
        subjectRepository.removeSubject(school, deleted);
        return deleted;
    }

    public void editSchoolInteractive(Scanner in) {
        SchoolRepository schoolRepository = new SchoolRepository();
        School school = School.getSchool();
        System.out.println("Enter the name of the school");
        String schoolName = in.nextLine();
        school.setName(schoolName);
        rwService.logger("EditSchool - " + new Date().toString());
        schoolRepository.updateSchool(school);
    }

    public void deleteClassInteractive(Scanner in) {
        School school = School.getSchool();
        List<Class> classes = school.getClasses();
        printList(classes);
        System.out.println("Choose the class to delete:");
        int choice = Integer.parseInt(in.nextLine());
        removeClass(choice);
    }

    public void deleteStudentInteractive(Scanner in) {
        School school = School.getSchool();
        List<Student> students = school.getStudents();
        // System.out.println("Enter the name of the student:");
        // int studentIndex = Integer.parseInt(in.nextLine());
        // String studentName = in.nextLine();
        // List<Student> studentsNamed = students.stream()
        //         .filter(stud -> studentName.contains(stud.getLastName())
        //                 && studentName.contains(stud.getFirstName()))
        //         .collect(Collectors.toList());
        // printList(studentsNamed);
        printList(students);
        System.out.println("Choose the student to delete:");
        int choice = Integer.parseInt(in.nextLine());
        removeStudent(choice);
    }

    public void deleteTeacherInteractive(Scanner in) {
        School school = School.getSchool();
        List<Teacher> teachers = school.getTeachers();
        // System.out.println("Enter the name of the teacher:");
        // String teacherName = in.nextLine();
        // List<Teacher> teachersNamed = teachers.stream()
        //         .filter(stud -> teacherName.contains(stud.getLastName())
        //                 && teacherName.contains(stud.getFirstName()))
        //         .collect(Collectors.toList());
        printList(teachers);
        System.out.println("Choose the teacher to delete:");
        int choice = Integer.parseInt(in.nextLine());
        removeTeacher(choice);
    }

    public void deleteProgramInteractive(Scanner in) {
        School school = School.getSchool();
        List<Program> programs = school.getPrograms();
        printList(programs);
        System.out.println("Choose the program to delete:");
        int choice = Integer.parseInt(in.nextLine());
        removeProgram(choice);
    }

    public void deleteSubjectInteractive(Scanner in) {
        School school = School.getSchool();
        List<Subject> subjects = school.getSubjects();
        printList(subjects);
        System.out.println("Choose the subject to delete:");
        int choice = Integer.parseInt(in.nextLine());
        removeSubject(choice);
    }

    public void deleteSchoolInteractive(Scanner in) {
        System.out.println("Enter what to delete class/student/teacher/program/subject");
        String elementDeleted = in.nextLine();
        switch (elementDeleted) {
            case "class":
                deleteClassInteractive(in);
                break;
            case "student":
                deleteStudentInteractive(in);
                break;
            case "teacher":
                deleteTeacherInteractive(in);
                break;
            case "program":
                deleteProgramInteractive(in);
                break;
            case "subject":
                deleteSubjectInteractive(in);
                break;
        }
    }

    public void addStudentInteractive(Scanner in) {
        try {
            School school = School.getSchool();
            List<Class> classes = school.getClasses();
            printList(classes);
            System.out.println("Enter the student class index:");
            int choice = Integer.parseInt(in.nextLine());
            Class studentClass = classes.get(choice);
            System.out.println("Enter the student first name:");
            String firstName = in.nextLine();
            System.out.println("Enter the student last name:");
            String lastName = in.nextLine();
            System.out.println("Enter the student pid:");
            String pid = in.nextLine();
            System.out.println("Enter the student birthdate(dd/mm/yyyy):");
            String dateString = in.nextLine();
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            addStudent(new Student(
                    firstName,
                    lastName,
                    date,
                    pid,
                    studentClass
            ));
        } catch (ParseException e) {
            System.out.println("Invalid date entered!");
        }
    }

    public void addTeacherInteractive(Scanner in) {
        try {
            System.out.println("Enter the teacher first name:");
            String firstName = in.nextLine();
            System.out.println("Enter the teacher last name:");
            String lastName = in.nextLine();
            System.out.println("Enter the teacher pid:");
            String pid = in.nextLine();
            System.out.println("Enter the teacher birthdate(dd/mm/yyyy):");
            String dateString = in.nextLine();
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            addTeacher(new Teacher(
                    firstName,
                    lastName,
                    date,
                    pid
            ));
        } catch (ParseException e) {
            System.out.println("Invalid date entered!");
        }

    }

    public void addProgramInteractive(Scanner in) {
        System.out.println("Enter the program name:");
        String programName = in.nextLine();
        System.out.println("Enter the program duration (years):");
        int duration = Integer.parseInt(in.nextLine());
        addProgram(new Program(
                programName,
                duration
        ));

    }

    public void addSubjectInteractive(Scanner in) {
        System.out.println("Enter the subject name:");
        String subjectName = in.nextLine();
        System.out.println("Enter the subject domain:");
        String subjectDomain = in.nextLine();
        addSubject(new Subject(
                subjectName,
                subjectDomain
        ));
    }

    public void addSchoolInteractive(Scanner in) {
        System.out.println("Enter what to add student/teacher/program/subject/class");
        String elementAdded = in.nextLine();
        switch (elementAdded) {
            case "class":
                addClassInteractive(in);
                break;
            case "student":
                addStudentInteractive(in);
                break;
            case "teacher":
                addTeacherInteractive(in);
                break;
            case "program":
                addProgramInteractive(in);
                break;
            case "subject":
                addSubjectInteractive(in);
                break;
        }
    }

    public void showSchool() {
        CatalogueService catalogueService = CatalogueService.getCatalogueService();
        School school = School.getSchool();
        System.out.println(school);
        System.out.println("Has classes:");
        for (Class schoolClass : school.getClasses()) {
            System.out.println(schoolClass);
            System.out.println("Has marks:");
            catalogueService.showGrades(schoolClass);
        }
        rwService.logger("ShowSchool - " + new Date().getTime());
    }

    public <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }
}
