DELETE
FROM studcourses;

DELETE
FROM course;

DELETE
FROM student;

DELETE
FROM instructor;

INSERT INTO instructor (instructid, instructname)
    VALUES(1, 'T Sally'),
          (2, 'T Lucy'),
          (3, 'T Charlie');

INSERT INTO course (courseid, coursename, instructid)
	VALUES (1, 'T Data Science', 1),
           (2, 'T JavaScript', 1),
           (3, 'T Node.js',  1),
           (4, 'T Java Back End', 2),
           (5, 'T Mobile IOS', 2),
           (6, 'T Mobile Android',  3);

INSERT INTO student (studid, studname)
    VALUES (1, 'T John'),
           (2, 'T Julian'),
           (3, 'T Mary'),
           (4, 'T Julian'),
           (5, 'T Tyler'),
           (6, 'T Kim'),
           (7, 'T Juan'),
           (8, 'T Robby'),
           (9, 'T Roberto'),
           (10, 'T Bob'),
           (11, 'T Liz'),
           (12, 'T June'),
           (13, 'T April');

INSERT INTO studcourses (studid, courseid)
    VALUES (1, 1),
           (1, 4),
           (2, 2),
           (3, 3),
           (3, 1),
           (3, 6);

alter sequence hibernate_sequence restart with 20;