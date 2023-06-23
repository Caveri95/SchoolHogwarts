package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.DTO.StudentDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    public StudentDTO createStudent(Student student) {
        studentRepository.save(student);
        return mapToStudentDTO(student);
    }

    public StudentDTO findStudent(long id) {
        return mapToStudentDTO(studentRepository.findById(id).get());
    }

    public StudentDTO editStudent(Student student) {
        studentRepository.save(student);
        return mapToStudentDTO(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getStudentsByAge(Integer minAge, Integer maxAge) {
        List<Student> studentByAgeBetween = studentRepository.findStudentByAgeBetween(minAge, maxAge);
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : studentByAgeBetween) {
            StudentDTO studentDTO = mapToStudentDTO(student);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOs = new ArrayList<>();

        for (Student student : students) {
            StudentDTO studentDTO = mapToStudentDTO(student);
            studentDTOs.add(studentDTO);
        }
        return studentDTOs;
    }

    public List<StudentDTO> getStudentsByIdFaculty(long id) {
        List<Student> studentByFacultyId = studentRepository.findStudentByFacultyId(id);
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : studentByFacultyId) {
            StudentDTO studentDTO = mapToStudentDTO(student);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

       private StudentDTO mapToStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setAge(student.getAge());
        studentDTO.setFacultyId(student.getFaculty().getId());
        return studentDTO;
    }

    private Student mapToStudent(StudentDTO studentDTO) {
        return new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getAge(),
                facultyRepository.findFacultyById(studentDTO.getFacultyId()));
    }



}
