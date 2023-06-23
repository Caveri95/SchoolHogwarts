package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class FacultyController {

    private final FacultyService houseService;

    public FacultyController(FacultyService facultyService) {
        this.houseService = facultyService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Найти факультет по его id", description = "Введите id факультета")
    public ResponseEntity<Faculty> findFacultyById(@PathVariable long id) {
        Faculty faculty = houseService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    @Operation(summary = "Получить список факультетов по имени либо вывести список всех имеющихся факультетов")
    public ResponseEntity<Collection<Faculty>> getAllFacultiesOrByName(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(houseService.getAllFacultiesOrByName(name));
    }

    @GetMapping("/color")
    @Operation(summary = "Найти факультет по его цвету", description = "Введите цвет факультета")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@RequestParam String color) {
        return ResponseEntity.ok(houseService.getFacultyByColor(color));
    }

    @PostMapping
    @Operation(summary = "Добавить новый факультет", description = "Введите название факультета и его цвет")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return houseService.createFaculty(faculty);
    }

    @PutMapping
    @Operation(summary = "Редактирование факультета", description = "Введите id факультета, его название и цвет для редактирования")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editFaculty = houseService.editFaculty(faculty);
        if (editFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета", description = "Введите id факультета, который необходимо удалить")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        houseService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
