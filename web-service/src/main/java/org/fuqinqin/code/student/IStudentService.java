package org.fuqinqin.code.student;

import org.fuqinqin.code.gen.student.Student;

import java.util.List;

public interface IStudentService {

    Long add(Student student);

    Student getById(Long sNo);

    List<Student> findAll();

}
