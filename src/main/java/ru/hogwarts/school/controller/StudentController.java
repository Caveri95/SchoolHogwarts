package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Найти студента по его id", description = "Введите id студента")
    public ResponseEntity<Student> findStudentById(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping()
    @Operation(summary = "Получить всех студентов по возрасту либо вывести список всех имеющихся студентов",
    description = "Введите значение минимального и максимального возраста для поиска либо оставьте пустыми для вывода всех студентов," +
            "для поиска по конкретному возрасту введите этот возраст в оба поля")
    public ResponseEntity<Collection<Student>> getAllStudentsOrByAge(@RequestParam(required = false) Integer minAge, @RequestParam(required = false) Integer maxAge) {
        return ResponseEntity.ok(studentService.getAllStudentsOrByAge(minAge, maxAge));
    }

    /*@GetMapping("/age")
    @Operation(summary = "Найти студента по его возрасту", description = "Введите возраст студента")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam int age) {
        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }*/

    @PostMapping
    @Operation(summary = "Добавить нового студента", description = "Введите id студента, его имя и возраст")
    public Student addStudent(@RequestBody Student student) {

        return studentService.createStudent(student);
    }

    @PutMapping
    @Operation(summary = "Редактирование студента", description = "Введите id студента, его имя и возраст для редактирования")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editStudent = studentService.editStudent(student);
        if (editStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента", description = "Введите id студента, которого необходимо удалить")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
