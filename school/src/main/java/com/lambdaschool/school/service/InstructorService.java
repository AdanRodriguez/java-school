package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Instructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface InstructorService
{
    ArrayList<Instructor> findAll(Pageable pageable);

    Instructor save(Instructor instructor);

    Instructor update(Instructor instructor, long id);

    void delete(long id);
}