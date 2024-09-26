package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.InstructorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/instructors")
public class InstructorController
{
    @Autowired
    private InstructorService instructorService;

    //GET localhost:2019/instructors/instructors
    @ApiOperation(value = "returns all Instructors", response = Instructor.class, responseContainer = "List")
    @ApiImplicitParams({
                               @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                                                 value = "Results page you want to retrieve (0..N)"),
                               @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                                                 value = "Number of records per page."),
                               @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                                                 value = "Sorting criteria in the format: property(,asc|desc). " +
                                                         "Default sort order is ascending. " +
                                                         "Multiple sort criteria are supported.")})

    @GetMapping(value = "/instructors", produces = {"application/json"})
    public ResponseEntity<?> listAllInstructors(@PageableDefault(page = 0, size = 3) Pageable pageable)
    {
        return new ResponseEntity<>(instructorService.findAll(pageable), HttpStatus.OK);
    }

    //POST localhost:2019/instructors/instructor
    @ApiOperation(value = "adds a new Instructor")
    @PostMapping(value = "/instructor", consumes = {"application/json"})
    public ResponseEntity<?> addInstructor(@Valid
                                           @RequestBody Instructor instructor)
    {
        instructorService.save(instructor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //PUT localhost:2019/instructors/instructor/21
    @ApiOperation(value = "updates an Instructor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Instructor updated", response = Instructor.class),
            @ApiResponse(code = 404, message = "Instructor Not Found", response = ErrorDetail.class)})
    @PutMapping(value = "/instructor/{instructid}",
                consumes = {"application/json"})
    public ResponseEntity<?> editInstructor(@RequestBody Instructor instructor,
                                            @ApiParam(value = "Student id",
                                                      required = true,
                                                      example = "1")
                                            @PathVariable long instructid)
    {
        instructorService.update(instructor, instructid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE localhost:2019/instructors/instructor/21
    @ApiOperation(value = "deletes a Instructor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Instructor deleted", response = Instructor.class),
            @ApiResponse(code = 404, message = "Instructor Not Found", response = ErrorDetail.class)})
    @DeleteMapping(value = "/instructor/{instructid}")
    public ResponseEntity<?> deleteInstructor(@PathVariable long instructid)
    {
        instructorService.delete(instructid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}