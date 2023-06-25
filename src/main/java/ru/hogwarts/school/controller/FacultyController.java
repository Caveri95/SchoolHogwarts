package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.DTO.FacultyDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Найти факультет по его id", description = "Введите id факультета")
    public ResponseEntity<FacultyDTO> findFacultyById(@PathVariable long id) {
        FacultyDTO facultyDTO = facultyService.findFaculty(id);
        if (facultyDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyDTO);
    }

    @GetMapping
    @Operation(summary = "Получить список факультетов по имени либо вывести список всех имеющихся факультетов")
    public ResponseEntity<List<FacultyDTO>> getAllFacultiesOrByName(@RequestParam(required = false ) String name) {
        return ResponseEntity.ok(facultyService.getAllFacultiesOrByName(name));
    }

    @GetMapping("/getFacultyByStudent")
    @Operation(summary = "Получение факультет по id студента")
    public ResponseEntity<FacultyDTO> getFacultyByIdStudent(@RequestParam long id) {
        return ResponseEntity.ok(facultyService.getFacultyByIdStudent(id));
    }

    @GetMapping("/color")
    @Operation(summary = "Найти факультет по его цвету", description = "Введите цвет факультета")
    public ResponseEntity<List<FacultyDTO>> getFacultyByColor(@RequestParam String color) {

        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
    }

    @PostMapping
    @Operation(summary = "Добавить новый факультет", description = "Введите название факультета и его цвет")
    public ResponseEntity<FacultyDTO> addFaculty(@RequestBody FacultyDTO facultyDTO) {
        return ResponseEntity.ok(facultyService.createFaculty(facultyDTO));
    }

    @PutMapping
    @Operation(summary = "Редактирование факультета", description = "Введите id факультета, его название и цвет для редактирования")
    public ResponseEntity<FacultyDTO> editFaculty(@RequestBody FacultyDTO facultyDTO) {
        FacultyDTO facultyDTO1 = facultyService.editFaculty(facultyDTO);
        if (facultyDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(facultyDTO);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета", description = "Введите id факультета, который необходимо удалить")
    public ResponseEntity<FacultyDTO> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
