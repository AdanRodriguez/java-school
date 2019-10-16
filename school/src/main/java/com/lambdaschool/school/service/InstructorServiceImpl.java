package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.repository.CourseRepository;
import com.lambdaschool.school.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
    @Autowired
    private InstructorRepository instructrepos;


    @Autowired
    private CourseRepository courserepos;

    @Override
    public ArrayList<Instructor> findAll(Pageable pageable)
    {
        ArrayList<Instructor> rtnList = new ArrayList<>();
        instructrepos.findAll().iterator().forEachRemaining(rtnList::add);
        return rtnList;
    }

    @Override
    public Instructor save(Instructor instructor)
    {
        Instructor newInstructor = new Instructor();

        newInstructor.setInstructname(instructor.getInstructname());

        return instructrepos.save(newInstructor);
    }

    @Override
    public Instructor update(Instructor instructor, long id)
    {
        Instructor currentInstructor = instructrepos.findInstructorByInstructid(id);

        if (instructor.getInstructname() != null)
        {
            currentInstructor.setInstructname(instructor.getInstructname());
        }

        return instructrepos.save(currentInstructor);
    }

    @Override
    public void delete(long id)
    {
        Instructor currentInstructor = instructrepos.findInstructorByInstructid(id);

        for (Course c : currentInstructor.getCourses())
        {
            c.setInstructor(null);
        }

        instructrepos.deleteById(id);
    }
}
