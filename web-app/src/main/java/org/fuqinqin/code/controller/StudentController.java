package org.fuqinqin.code.controller;

import org.fuqinqin.code.gen.student.Student;
import org.fuqinqin.code.student.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Autowired
    private IStudentService studentService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
        Long time = System.currentTimeMillis();

        Student student = new Student();
        student.setsName("stu_"+time);
        student.setsAge(23);
        student.setsAddress("address_"+time);

        Long id = studentService.add(student);
        if(id != null && id.intValue()>0){
            logger.info("插入成功，id={}", id);
        }

        return "id = "+id;
    }

    @RequestMapping(value = "/{sNo}", method = RequestMethod.GET)
    public Student findById(@PathVariable("sNo") Long sNo){
        return studentService.getById(sNo);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Student> findById(){
        return studentService.findAll();
    }

}
