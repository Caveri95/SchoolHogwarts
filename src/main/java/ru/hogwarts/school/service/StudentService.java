package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.DTO.StudentDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.interfaceForRequest.YoungerStudents;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;

import static ru.hogwarts.school.model.DTO.StudentDTO.mapToStudentDTO;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    public StudentDTO createStudent(StudentDTO studentDTO) {
        logger.info("Вызов метода для создания студента");
        Student student = mapToStudent(studentDTO);
        studentRepository.save(student);
        logger.info("Добавлен студент {}", studentDTO);
        return studentDTO;
    }

    public StudentDTO findStudent(long id) {
        logger.info("Вызов метода для поиска студента по id = {}", id);
        return mapToStudentDTO(studentRepository.findById(id).get());
    }

    public StudentDTO editStudent(StudentDTO studentDTO) {
        logger.info("Вызов метода для редактирования студента {}", studentDTO);
        Student student = mapToStudent(studentDTO);
        studentRepository.save(student);
        return studentDTO;
    }

    public void deleteStudent(long id) {
        logger.info("Вызов метода для удаления студента по id = {}", id);
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getStudentsByAge(Integer minAge, Integer maxAge) {
        logger.info("Вызов метода для поиска студентов по заданному диапазону возраста. От {}, до = {}",minAge, maxAge);
        List<Student> studentByAgeBetween = studentRepository.findStudentByAgeBetween(minAge, maxAge);
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : studentByAgeBetween) {
            StudentDTO studentDTO = mapToStudentDTO(student);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    public List<StudentDTO> getAllStudents(Integer pageNumber, Integer pageSize) {
        logger.info("Вызов метода для вывода всех студентов постранично. Номер страницы - {}, " +
                "количество студентов на странице - {}", pageNumber, pageSize);
        if (pageSize > 50 || pageSize <= 0) {
            pageSize = 50;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Student> students = studentRepository.findAll(pageRequest).getContent();
        List<StudentDTO> studentDTOs = new ArrayList<>();

        for (Student student : students) {
            StudentDTO studentDTO = mapToStudentDTO(student);
            studentDTOs.add(studentDTO);
        }
        return studentDTOs;
    }

    public List<StudentDTO> getStudentsByIdFaculty(long id) {
        logger.info("Вызов метода для поиска студентов по номеру факультета. Id = {}", id);
        List<Student> studentByFacultyId = studentRepository.findStudentByFacultyId(id);
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : studentByFacultyId) {
            StudentDTO studentDTO = mapToStudentDTO(student);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    public Integer findAVGStudentAge() {
        logger.info("Вызов метода для поиска среднего возраста студентов");
        return studentRepository.findAVGStudentAge();
    }

    public List<YoungerStudents> findYoungerStudents() {
        logger.info("Вызов метода для поиска пяти самых молодых студентов");
        return studentRepository.findYoungerStudents();
    }



    public Student mapToStudent(StudentDTO studentDTO) {
        return new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getAge(),
                facultyRepository.findFacultyById(studentDTO.getFacultyId()));
    }


}
