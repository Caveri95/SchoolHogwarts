package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.interfaceForRequest.YoungerStudents;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentByAge(int age);

    List<Student> findStudentByAgeBetween(int min, int max);

    List<Student> findStudentByFacultyId(long id);

    @Query(value = "SELECT AVG(student.age) FROM Student", nativeQuery = true)
    Integer findAVGStudentAge();

    @Query(value = "SELECT student.name, student.age FROM Student ORDER BY student.age LIMIT 5", nativeQuery = true)
    List<YoungerStudents> findYoungerStudents();



}
