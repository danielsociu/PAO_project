package services;

import models.*;
import models.Class;

import java.util.*;
import java.util.stream.Collectors;

public class ClassService {
    private static ClassService classService;
    private RWService rwService;

    private ClassService() {
        rwService = RWService.getRwService();
    }

    public static ClassService getClassService() {
        if (classService == null) {
            classService = new ClassService();
        }
        return classService;
    }

    public Class getCurrentClass(String year, String letter) {
        School school = School.getSchool();
        return school.getClasses()
                .stream()
                .filter(cls -> {
                    String[] years = cls.getYearPeriod().split("-");
                    return Integer.parseInt(years[0]) <= Calendar.getInstance().get(Calendar.YEAR)
                            && Integer.parseInt(years[1]) >= Calendar.getInstance().get(Calendar.YEAR);
                })
                .filter(cls -> (cls.getYear().equals(year) && cls.getLetter().equalsIgnoreCase(letter)))
                .findFirst()
                .orElse(null);
    }

    public void showClass(Class schoolClass) {
        System.out.println(schoolClass);
        rwService.logger("ShowClass - " + new Date().toString());
    }

    public void addStudent(Class schoolClass, Student student) {
        List<Student> classStudents = schoolClass.getStudents();
        student.setStudentClass(schoolClass);
        classStudents.add(student);

        schoolClass.setStudents(classStudents);

        String message = "Student " + student +
                " has been added to class " + schoolClass;
        rwService.addClassToObjectFile(schoolClass, student);
        rwService.logger("AddStudentToClass - " + new Date().toString());
        System.out.println(message);
    }

    public void removeStudent(Class schoolClass, Student student) {
        List<Student> classStudents = schoolClass.getStudents();
        classStudents.remove(student);

        schoolClass.setStudents(classStudents);

        String message = "Student " + student +
                " has been removed from class " + schoolClass;
        rwService.logger("RemoveStudentFromClass - " + new Date().toString());
        System.out.println(message);
    }

    public void addSubject(Class schoolClass, Subject subject, Teacher teacher) {
        Set<Class> teacherClasses = teacher.getClasses();
        if (!teacherClasses.contains(schoolClass)) {
            teacherClasses.add(schoolClass);
            teacher.setClasses(teacherClasses);
            rwService.addClassToObjectFile(teacher, schoolClass);
        }
        HashMap<Subject, Teacher> classSubjects = schoolClass.getSubjects();
        classSubjects.put(subject,teacher);
        schoolClass.setSubjects(classSubjects);

        String message = "Teacher " + teacher + " is now teaching " +
                subject + " in class " + schoolClass;
        rwService.addClassToObjectFile(schoolClass, subject, teacher);
        rwService.logger("AddSubjectToClass - " + new Date().toString());
        System.out.println(message);
    }
    public void removeSubject(Class schoolClass, Subject subject) {
        HashMap<Subject, Teacher> classSubjects = schoolClass.getSubjects();
        classSubjects.remove(subject);

        schoolClass.setSubjects(classSubjects);

        String message = "Subject " + subject +
                " has been removed from class " + schoolClass;
        rwService.logger("RemoveSubjectFromClass - " + new Date().toString());
        System.out.println(message);
    }

    public void editStudentInClassInteractive(Scanner in, Class schoolClass, String operation) {
        School school = School.getSchool();
        List<Student> students = school.getStudents();
        printList(students);

        // System.out.println("Enter the student's name:");
        // String studentName = in.nextLine();
        // List<Student> studentsNamed = students.stream()
        //         .filter(std -> (studentName.contains(std.getFirstName())
        //                 && studentName.contains(std.getLastName())))
        //         .collect(Collectors.toList());
        // printList(studentsNamed);

        System.out.println("Choose the student:");
        int position = Integer.parseInt(in.nextLine());
        if (operation.equalsIgnoreCase("add")) {
            addStudent(schoolClass, students.get(position));
        } else if (operation.equalsIgnoreCase("delete")) {
            removeStudent(schoolClass, students.get(position));
        }
    }

    public void editSubjectInClassInteractive(Scanner in, Class schoolClass, String operation) {
        School school = School.getSchool();
        List<Subject> subjects = school.getSubjects();
        printList(subjects);
        System.out.println("Enter the subject's digit:");
        int subjectIndex = Integer.parseInt(in.nextLine());
        //String subjectName = in.nextLine().toLowerCase();
        Subject mySubject = subjects.get(subjectIndex);
        // Subject mySubject = subjects.stream()
        //         .filter(sub -> subjectName.contains(sub.getName().toLowerCase()))
        //         .findFirst()
        //         .orElse(null);
        if (operation.equalsIgnoreCase("add")) {
            List<Teacher> teachers = school.getTeachers();
            printList(teachers);
            // System.out.println("Enter the teacher's name:");
            // String teacherName = in.nextLine();
            // List<Teacher> teachersNamed = teachers.stream()
            //         .filter(std -> (teacherName.contains(std.getFirstName())
            //                 && teacherName.contains(std.getLastName())))
            //         .collect(Collectors.toList());
            // printList(teachersNamed);
            System.out.println("Choose the teacher:");
            int position = Integer.parseInt(in.nextLine());
            addSubject(schoolClass, mySubject, teachers.get(position) );
        } else if (operation.equalsIgnoreCase("delete")) {
            removeSubject(schoolClass, mySubject);
        }
    }

    public void editClassProgramInteractive(Scanner in, Class schoolClass) {
        School school = School.getSchool();
        List<Program> programs = school.getPrograms();
        printList(programs);
        System.out.println("Choose the program's number");
        int position = Integer.parseInt(in.nextLine());
        schoolClass.setProgram(programs.get(position));
        rwService.logger("EditProgram - " + new Date().toString());
    }

    public void editClassInteractive(Scanner in, Class schoolClass) {
        System.out.println("You can edit one of the fields: year, yearPeriod, letter or add a program/student/subject");
        String option = in.nextLine();
        if ("year yearPeriod letter".contains(option)) {
            System.out.println("Enter the " + option);
            String answer = in.nextLine();
            switch (option) {
                case "year":
                    schoolClass.setYear(answer);
                    rwService.logger("EditClassYear - " + new Date().toString());
                    break;
                case "yearPeriod":
                    schoolClass.setYearPeriod(answer);
                    rwService.logger("EditClassYearPeriod - " + new Date().toString());
                    break;
                case "letter":
                    schoolClass.setLetter(answer);
                    rwService.logger("EditClassLetter - " + new Date().toString());
                    break;
            }
        } else {
            int position;
            switch (option) {
                case "program":
                    editClassProgramInteractive(in, schoolClass);
                    break;
                case "student":
                    editStudentInClassInteractive(in, schoolClass, "add");
                    break;
                case "subject":
                    editSubjectInClassInteractive(in, schoolClass, "add");
                    break;
            }
        }
    }

    public void deleteClassInteractive(Scanner in, Class schoolClass) {
        System.out.println("Do you want to delete a student/subject");
        String option = in.nextLine();
        if (option.equalsIgnoreCase("student")) {
            editStudentInClassInteractive(in, schoolClass, "delete");
        } else if (option.equalsIgnoreCase("subject")){
            editSubjectInClassInteractive(in, schoolClass, "delete");
        }
    }

    private <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }
}
