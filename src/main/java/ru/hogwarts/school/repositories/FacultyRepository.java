package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.interfaceForRequest.StudentsByFaculty;

import java.util.List;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findAllByColorIgnoreCase(String color);

    List<Faculty> findFacultiesByNameIgnoreCase(String name);

    Faculty findFacultyById(long id);

    @Query(value = " SELECT faculty.name AS facultyName, COUNT(student.name) AS amount FROM faculty LEFT JOIN student ON faculty.id=student.faculty_id GROUP BY faculty.name", nativeQuery = true)
    List<StudentsByFaculty> getAmountStudentsByFaculty();
    @Query(value = "select COUNT(*) FROM student", nativeQuery = true)
    Long getAmountStudents();


}
