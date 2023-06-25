package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.DTO.StudentDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Найти студента по его id", description = "Введите id студента")
    public ResponseEntity<StudentDTO> findStudentById(@PathVariable long id) {
        StudentDTO studentDTO = studentService.findStudent(id);
        if (studentDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentDTO);
    }

    @GetMapping()
    @Operation(summary = "Получить всех студентов по заданному возрасту ",
            description = "Введите значение минимального и максимального возраста для поиска либо, для поиска по конкретному возрасту введите этот возраст в оба поля")
    public ResponseEntity<Collection<StudentDTO>> getStudentsByAge(@RequestParam(required = false) Integer minAge, @RequestParam(required = false) Integer maxAge) {
        return ResponseEntity.ok(studentService.getStudentsByAge(minAge, maxAge));
    }

    @GetMapping("/getAllStudents")
    @Operation(summary = "Получить всех студентов")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/getStudentsByFaculty")
    @Operation(summary = "Получить студентов на факультете")
    public ResponseEntity<List<StudentDTO>> getStudentsByIdFaculty(long id) {
        return ResponseEntity.ok(studentService.getStudentsByIdFaculty(id));
    }

    @PostMapping
    @Operation(summary = "Добавить нового студента", description = "Введите id студента, его имя и возраст")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO) {

        return ResponseEntity.ok(studentService.createStudent(studentDTO));
    }

    @PutMapping
    @Operation(summary = "Редактирование студента", description = "Введите id студента, его имя, возраст и номер факультета для редактирования")
    public ResponseEntity<StudentDTO> editStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO studentDTO1 = studentService.editStudent(studentDTO);
        if (studentDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentDTO);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента", description = "Введите id студента, которого необходимо удалить")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
