package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseController.class, secure = false)
public class CourseControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private ArrayList<Course> courseList;

    @Before
    public void setUp()
    {
        courseList = new ArrayList<>();

        Instructor i1 = new Instructor("Sally");
        i1.setInstructid(1);

        Student s1 = new Student("John");
        s1.setStudid(1);
        Student s2 = new Student("Julian");
        s1.setStudid(2);
        Student s3 = new Student("Mary");
        s1.setStudid(3);

        Course c1 = new Course("Data Science");
        c1.setCourseid(1);
        c1.setInstructor(i1);
        c1.getStudents().add(s1);
        c1.getStudents().add(s3);

        courseList.add(c1);

        Course c2 = new Course("JavaScript");
        c2.setCourseid(2);
        c2.setInstructor(i1);
        c2.getStudents().add(s2);

        courseList.add(c2);

        Course c3 = new Course("Node.js");
        c3.setCourseid(3);
        c3.setInstructor(i1);
        c3.getStudents().add(s3);

        courseList.add(c3);
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void listAllCourses() throws Exception
    {
        String apiUrl = "/courses/courses";

        Mockito.when(courseService.findAll()).thenReturn(courseList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(rb).andReturn();
        String testResult = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(courseList);

        assertEquals(expectedResult, testResult);
    }

    @Test
    public void addNewCourse() throws Exception
    {
        String apiUrl = "/courses/course/add";

        Instructor i1 = new Instructor();
        i1.setInstructid(1);

        Course c1 = new Course("Test Course");
        c1.setInstructor(i1);

        Mockito.when(courseService.save(any(Course.class))).thenReturn(c1);

        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(c1);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(expectedResult);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }
}
