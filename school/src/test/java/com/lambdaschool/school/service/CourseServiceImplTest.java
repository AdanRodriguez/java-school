package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplicationTests;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplicationTests.class)
public class CourseServiceImplTest
{
    @Autowired
    private CourseService courseService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void delete()
    {
        courseService.delete(2L);
        assertEquals(5, courseService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotFound()
    {
        courseService.delete(313L);
        assertEquals(5, courseService.findAll().size());
    }

    @Test
    public void findCourseById()
    {
        assertEquals("T Data Science", courseService.findCourseById(1L).getCoursename());
    }

    @Test
    public void save()
    {
        Instructor i1 = new Instructor();
        i1.setInstructid(1);

        Course c1 = new Course("Test Course");
        c1.setInstructor(i1);

        Course addCourse = courseService.save(c1);

        assertNotNull(addCourse);

        Course foundCourse = courseService.findCourseById(addCourse.getCourseid());

        assertEquals(addCourse.getCoursename(), foundCourse.getCoursename());
    }
}