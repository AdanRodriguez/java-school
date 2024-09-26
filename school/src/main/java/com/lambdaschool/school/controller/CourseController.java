package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    //GET localhost:2019/courses/courses/paginal
    @ApiOperation(value = "returns all Courses", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
                               @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                                                 value = "Results page you want to retrieve (0..N)"),
                               @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                                                 value = "Number of records per page."),
                               @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                                                 value = "Sorting criteria in the format: property(,asc|desc). " +
                                                         "Default sort order is ascending. " +
                                                         "Multiple sort criteria are supported.")})

    @GetMapping(value = "/courses/paginal", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(HttpServletRequest request, @PageableDefault(page = 0, size = 3)Pageable pageable)
    {
        logger.warn("This is a log");
        logger.trace("This is another log");

        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed!");
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    ////GET localhost:2019/courses/courses
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(HttpServletRequest request)
    {
        logger.warn("This is a log");
        logger.trace("This is another log");

        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed!");
        ArrayList<Course> myCourses = courseService.findAll();
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    //GET localhost:2019/courses/studcount
    @ApiOperation(value = "returns all Courses with Students", response = CountStudentsInCourses.class, responseContainer = "List")
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses(HttpServletRequest request)
    {
        logger.warn("This is a log");
        logger.trace("This is another log");

        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed!");
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    //DELETE localhost:2019/courses/courses/1
    @ApiOperation(value = "deletes a Course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Course deleted", response = Course.class),
            @ApiResponse(code = 404, message = "Course Not Found", response = ErrorDetail.class)})
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseid, HttpServletRequest request)
    {
        logger.warn("This is a log");
        logger.trace("This is another log");

        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed!" + courseid);
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/course/add",
                 consumes = {"application/json"})
    public ResponseEntity<?> addNewCourse(@Valid
                                          @RequestBody Course course)
    {
        courseService.save(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
