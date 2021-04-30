package services;

import helpers.FileWritable;
import models.*;
import models.Class;

import java.io.*;
import java.util.*;

public class RWService {
    private static RWService rwService;
    private final String path = "csvDatabase/school/";
    private final String pathRelations = "csvDatabase/school/relations/";
    private final String fileType = ".csv";

    private RWService () {
        new File(path).mkdirs();
        new File(pathRelations).mkdirs();
    }

    public static RWService getRwService() {
        if (rwService == null) {
            rwService = new RWService();
        }
        return rwService;
    }

    public void readAllData(School school) {
        // Reading all the programs
        // String[] v, v[0] will always be the hashcode of the object
        // Reading Programs
        String programsPath = path + "programs" + fileType;
        File programData = new File(programsPath);
        if (programData.exists()) {
            readPrograms(programsPath);
        }

        // Reading Subjects
        String subjectsPath = path + "subjects" + fileType;
        File subjectData = new File(subjectsPath);
        if (subjectData.exists()) {
            readSubjects(subjectsPath);
        }

        // Reading Classes
        String classesPath = path + "classes" + fileType;
        File classData = new File(classesPath);
        if (classData.exists()) {
            readClasses(school, classesPath);
        }

        // Reading Students
        String studentsPath = path + "students" + fileType;
        File studentData = new File(studentsPath);
        if (studentData.exists()) {
            readStudents(school, studentsPath);
        }

        // Reading Teachers
        String teachersPath = path + "teachers" + fileType;
        File teacherData = new File(teachersPath);
        if (teacherData.exists()) {
            readTeachers(teachersPath);
        }

        // Reading the teacher's classes
        try {
            for (Teacher teacher: school.getTeachers()) {
                String teacherClassesPath = pathRelations + teacher.hashCode() + "classes" + fileType;
                File teacherClassesFile = new File(teacherClassesPath);
                if (teacherClassesFile.exists()) {
                    Set<Class> classes = new HashSet<>();
                    BufferedReader fin = new BufferedReader(new FileReader(teacherClassesPath));
                    String line = null;
                    while ((line = fin.readLine()) != null) {
                        String[] teacherClassesData = line.split(",");
                        Class teacherClass = getClassFromPID(school, teacherClassesData[0]);
                        classes.add(teacherClass);
                    }
                    teacher.setClasses(classes);
                    fin.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't read teacher's classes");
        }

        // Reading the class's students
        try {
            for (Class myClass: school.getClasses()) {
                String classStudentsPath = pathRelations + myClass.hashCode() + "students" + fileType;
                File classStudentsFile = new File(classStudentsPath);
                if (classStudentsFile.exists()) {
                    List<Student> students = new ArrayList<>();
                    BufferedReader fin = new BufferedReader(new FileReader(classStudentsPath));
                    String line = null;
                    while ((line = fin.readLine()) != null) {
                        String[] classStudentsData = line.split(",");
                        Student classStudent = getStudentFromPID(school, classStudentsData[0]);
                        students.add(classStudent);
                    }
                    myClass.setStudents(students);
                    fin.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't read class's students");
        }

        // Reading the class's subjects
        try {
            for (Class myClass: school.getClasses()) {
                String classSubjectsPath = pathRelations + myClass.hashCode() + "subjects" + "teachers" + fileType;
                File classSubjectsFile = new File(classSubjectsPath);
                if (classSubjectsFile.exists()) {
                    HashMap<Subject, Teacher> subjects = new HashMap<>();
                    BufferedReader fin = new BufferedReader(new FileReader(classSubjectsPath));
                    String line = null;
                    while ((line = fin.readLine()) != null) {
                        String[] classSubjectsData = line.split(",");
                        Subject classSubject = getSubjectFromPID(school, classSubjectsData[0]);
                        Teacher classTeacher = getTeacherFromPID(school, classSubjectsData[1]);
                        subjects.put(classSubject, classTeacher);
                    }
                    myClass.setSubjects(subjects);
                    fin.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't read class's subjects");
        }

        // Reading the class catalogue's grades and absences
        for (Class myClass: school.getClasses()) {
            List<Grade> grades = new ArrayList<>();
            List<Absence> absences = new ArrayList<>();
            Catalogue catalogue = new Catalogue();
            String classGradesPath = pathRelations + myClass.hashCode() + "grades" + fileType;
            File classGradesFile = new File(classGradesPath);
            if (classGradesFile.exists()) {
                grades = readGrades(school, classGradesPath);
            }
            String classAbsencesPath = pathRelations + myClass.hashCode() + "absences" + fileType;
            File classAbsencesFile = new File(classAbsencesPath);
            if (classAbsencesFile.exists()) {
                absences = readAbsences(school, classAbsencesPath);
            }
            myClass.setCatalogue(new Catalogue(grades, absences));
        }
    }

    public void readPrograms(String filePath) {
        SchoolService schoolService = SchoolService.getSchoolService();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = fin.readLine()) != null) {
                String[] programData = line.split(",");
                Program program = new Program(programData[1], Integer.parseInt(programData[2]));
                schoolService.addProgram(program);
            }
            fin.close();
        } catch (IOException e) {
            System.out.println("Couldn't read from file");
        }
    }

    public void readSubjects(String filePath) {
        SchoolService schoolService = SchoolService.getSchoolService();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = fin.readLine()) != null) {
                String[] subjectData = line.split(",");
                Subject subject = new Subject(subjectData[1], subjectData[2]);
                schoolService.addSubject(subject);
            }
            fin.close();
        } catch (IOException e) {
            System.out.println("Couldn't read from file");
        }
    }

    public void readClasses(School school, String filePath) {
        SchoolService schoolService = SchoolService.getSchoolService();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = fin.readLine()) != null) {
                String[] classData = line.split(",");
                Program program = getProgramFromPID(school, classData[4]);
                Class myClass = new Class.Builder()
                        .withYear(classData[1])
                        .withLetter(classData[2])
                        .withYearPeriod(classData[3])
                        .withProgram(program)
                        .build();
                schoolService.addClass(myClass);
            }
            fin.close();
        } catch (IOException e) {
            System.out.println("Couldn't read from file");
        }
    }

    public void readStudents(School school, String filePath) {
        SchoolService schoolService = SchoolService.getSchoolService();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = fin.readLine()) != null) {
                String[] studentData = line.split(",");
                Class studentClass = getClassFromPID(school, studentData[5]);
                Student student = new Student(
                        studentData[1],
                        studentData[2],
                        new Date(Long.parseLong(studentData[3])),
                        studentData[4],
                        studentClass
                    );
                schoolService.addStudent(student);
            }
            fin.close();
        } catch (IOException e) {
            System.out.println("Couldn't read from file");
        }
    }

    public void readTeachers(String filePath) {
        SchoolService schoolService = SchoolService.getSchoolService();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = fin.readLine()) != null) {
                String[] teacherData = line.split(",");
                Teacher teacher = new Teacher(
                        teacherData[1],
                        teacherData[2],
                        new Date(Long.parseLong(teacherData[3])),
                        teacherData[4]
                );
                schoolService.addTeacher(teacher);
            }
            fin.close();
        } catch (IOException e) {
            System.out.println("Couldn't read from file");
        }
    }

    public List<Grade> readGrades(School school, String filePath) {
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filePath));
            List<Grade> grades = new ArrayList<>();
            String line = null;
            while ((line = fin.readLine()) != null) {
                String[] gradeData = line.split(",");
                Teacher teacher = getTeacherFromPID(school, gradeData[4]);
                Student student = getStudentFromPID(school, gradeData[5]);
                Subject subject = getSubjectFromPID(school, gradeData[6]);
                Grade grade = new Grade.Builder()
                        .withScore(Double.parseDouble(gradeData[1]))
                        .withDate(new Date(Long.parseLong(gradeData[2])))
                        .withEvaluationMethod(gradeData[3])
                        .withTeacher(teacher)
                        .withStudent(student)
                        .withSubject(subject)
                        .build();
                grades.add(grade);
            }
            fin.close();
            return grades;
        } catch (IOException e) {
            System.out.println("Couldn't read from file");
            return null;
        }
    }

    public List<Absence> readAbsences(School school, String filePath) {
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filePath));
            List<Absence> absences = new ArrayList<>();
            String line = null;
            while ((line = fin.readLine()) != null) {
                String[] absenceData = line.split(",");
                Teacher teacher = getTeacherFromPID(school, absenceData[3]);
                Student student = getStudentFromPID(school, absenceData[4]);
                Subject subject = getSubjectFromPID(school, absenceData[5]);
                Absence absence = new Absence.Builder()
                        .withMotivated(Boolean.parseBoolean(absenceData[1]))
                        .withDate(new Date(Long.parseLong(absenceData[2])))
                        .withTeacher(teacher)
                        .withStudent(student)
                        .withSubject(subject)
                        .build();
                absences.add(absence);
            }
            fin.close();
            return absences;
        } catch (IOException e) {
            System.out.println("Couldn't read from file");
            return null;
        }
    }

    public Class getClassFromPID(School school, String classPID) {
        for (Class myClass: school.getClasses()) {
            if (String.valueOf(myClass.hashCode()).equals(classPID)) {
                return myClass;
            }
        }
        return null;
    }

    public Program getProgramFromPID(School school, String programPID) {
        for (Program program: school.getPrograms()) {
            if (String.valueOf(program.hashCode()).equals(programPID)) {
                return program;
            }
        }
        return null;
    }

    public Subject getSubjectFromPID(School school, String subjectPID) {
        for (Subject subject: school.getSubjects()) {
            if (String.valueOf(subject.hashCode()).equals(subjectPID)) {
                return subject;
            }
        }
        return null;
    }

    public Student getStudentFromPID(School school, String studentPID) {
        for (Student student: school.getStudents()) {
            if (String.valueOf(student.hashCode()).equals(studentPID)) {
                return student;
            }
        }
        return null;
    }

    public Teacher getTeacherFromPID(School school, String teacherPID) {
        for (Teacher teacher: school.getTeachers()) {
            if (String.valueOf(teacher.hashCode()).equals(teacherPID)) {
                return teacher;
            }
        }
        return null;
    }

    public <T extends FileWritable> void addClassToFile(T dataClass) {
        try {
            String filePath = path + dataClass.getFileName() + fileType;
            if (checkUnique(filePath, dataClass.toCSVString())) {
                FileWriter fout = new FileWriter(filePath, true);
                fout.write(dataClass.toCSVString());
                fout.write("\n");
                fout.close();
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to file");
        }
    }

    public <S, T extends FileWritable> void addClassToFile(S objectClass, T dataClass) {
        try {
            String filePath = pathRelations + objectClass.hashCode() + dataClass.getFileName() + fileType;
            if (checkUnique(filePath, dataClass.toCSVString())) {
                FileWriter fout = new FileWriter(filePath, true);
                fout.write(dataClass.toCSVString());
                fout.write("\n");
                fout.close();
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to file");
        }
    }

    public <S ,T extends FileWritable> void addClassToObjectFile (S objectClass, T dataClass) {
        try {
            // For relations we use just the hashcodes
            String filePath = pathRelations + objectClass.hashCode() + dataClass.getFileName() + fileType;
            if (checkUnique(filePath, String.valueOf(dataClass.hashCode()))) {
                FileWriter fout = new FileWriter(filePath, true);
                fout.write(String.valueOf(dataClass.hashCode()));
                fout.write("\n");
                fout.close();
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to file");
        }
    }

    public <S, T extends FileWritable, W extends FileWritable > void addClassToObjectFile (S objectClass, T dataClass1, W dataClass2) {
        try {
            // For relations we use just the hashcodes
            String filePath = pathRelations + objectClass.hashCode() + dataClass1.getFileName() + dataClass2.getFileName() + fileType;
            String dataWritten = String.valueOf(dataClass1.hashCode()) + ',' + dataClass2.hashCode();
            if (checkUnique(filePath, dataWritten)) {
                FileWriter fout = new FileWriter(filePath, true);
                fout.write(dataWritten);
                fout.write("\n");
                fout.close();
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to file");
        }
    }

    public void logger (String log) {
        try {
            // For relations we use just the hashcodes
            FileWriter fout = new FileWriter(path + "logger" + fileType, true);
            fout.write(log);
            fout.write("\n");
            fout.close();
        } catch (IOException e) {
            System.out.println("Couldn't write to file");
        }
    }

    private boolean checkUnique(String filePath, String searched) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                BufferedReader fin = new BufferedReader(new FileReader(filePath));
                String line = null;
                while ((line = fin.readLine()) != null) {
                    if (line.equals(searched)) {
                        fin.close();
                        return false;
                    }
                }
                fin.close();
                return true;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Couldn't check uniqueness");
            return false;
        }
    }
}
