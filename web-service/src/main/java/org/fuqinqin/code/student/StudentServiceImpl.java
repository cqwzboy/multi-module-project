package org.fuqinqin.code.student;

import org.fuqinqin.code.gen.student.Student;
import org.fuqinqin.code.gen.student.StudentExample;
import org.fuqinqin.code.gen.student.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    public Long add(Student student) {
        int rownum = studentMapper.insertSelective(student);
        System.out.println("插入 "+rownum + " 条数据");

        return student.getsNo();
    }

    public Student getById(Long sNo) {
        return studentMapper.selectByPrimaryKey(sNo);
    }

    public List<Student> findAll() {
        List<Student> list = studentMapper.selectByExample(new StudentExample());
        return list;
    }
}
