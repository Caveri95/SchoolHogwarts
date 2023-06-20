package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.HouseService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> findFacultyById(@PathVariable long id) {
        Faculty faculty = houseService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(houseService.getAllFaculties());
    }

    @GetMapping("/color")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@RequestParam String color) {
        return ResponseEntity.ok(houseService.getFacultyByColor(color));
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return houseService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editFaculty = houseService.editFaculty(faculty);
        if (editFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFacultu(@PathVariable long id) {
        return houseService.deleteFaculty(id);
    }
}
