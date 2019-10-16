package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    // Please note there is no way to add students to course yet! --Done

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    // http://localhost:2019/students/students/?page=1&size=3
    // http://localhost:2019/students/students/?sort=city,desc&sort=name,asc
    //GET localhost:2019/students/students
    @ApiOperation(value = "returns all Students", response = Student.class, responseContainer = "List")
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents(HttpServletRequest request, @PageableDefault(page = 0, size = 3)Pageable pageable)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        List<Student> myStudents = studentService.findAll(pageable);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    //GET localhost:2019/students/student/1
    @ApiOperation(value = "returns single Student by id", response = Student.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/student/{studid}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentById(
            @ApiParam(value = "Student id", required = true, example = "1")
            @PathVariable
                    Long studid, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed!" + studid);

        Student r = studentService.findStudentById(studid);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    //GET localhost:2019/students/student/namelike/John
    @ApiOperation(value = "returns a Student by name", response = Student.class, responseContainer = "List")
    @GetMapping(value = "/student/namelike/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(
            @ApiParam(value = "parts of Student name", required = true, example = "Mike" )
            @PathVariable String name, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed! " + name);

        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    //POST localhost:2019/students/student/
    @ApiOperation(value = "adds a Student")
    @PostMapping(value = "/student",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Student newStudent, HttpServletRequest request) throws URISyntaxException
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    //PUT localhost:2019/students/student/20
    @ApiOperation(value = "updates a Student")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student updated", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @PutMapping(value = "/student/{studid}")
    public ResponseEntity<?> updateStudent(
            @RequestBody
                    Student updateStudent,
            @ApiParam(value = "Student id",
                      required = true,
                      example = "1")
            @PathVariable
                    long studid, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed! " + studid);

        studentService.update(updateStudent, studid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // DELETE localhost:2019/students/student/13
    @ApiOperation(value = "deletes a Student")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student deleted", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @DeleteMapping("/student/{studid}")
    public ResponseEntity<?> deleteStudentById(@PathVariable long studid,HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed " + studid);

        studentService.delete(studid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
