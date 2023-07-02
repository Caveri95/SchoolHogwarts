package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.DTO.StudentDTO;
import ru.hogwarts.school.model.interfaceForRequest.YoungerStudents;
import ru.hogwarts.school.service.StudentAvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;
    private final StudentAvatarService studentAvatarService;

    public StudentController(StudentService studentService, StudentAvatarService studentAvatarService) {
        this.studentService = studentService;
        this.studentAvatarService = studentAvatarService;
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
    public ResponseEntity<Collection<StudentDTO>> getStudentsByAge(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return ResponseEntity.ok(studentService.getStudentsByAge(minAge, maxAge));
    }

    @GetMapping("/getAllStudents")
    @Operation(summary = "Получить всех студентов")
    public ResponseEntity<List<StudentDTO>> getAllStudents(@RequestParam("page") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        return ResponseEntity.ok(studentService.getAllStudents(pageNumber, pageSize));
    }

    @GetMapping("/getStudentsByFaculty")
    @Operation(summary = "Получить студентов на факультете")
    public ResponseEntity<List<StudentDTO>> getStudentsByIdFaculty(long id) {
        return ResponseEntity.ok(studentService.getStudentsByIdFaculty(id));
    }

    @GetMapping("/findAVGStudentAge")
    @Operation(summary = "Получить средний возраст студентов в школе Хогвартс")
    public ResponseEntity<Integer> findAVGStudentAge() {
        return ResponseEntity.ok(studentService.findAVGStudentAge());
    }

    @GetMapping("/findYoungerStudents")
    @Operation(summary = "Найти 5 самых молодых студентов")
    public ResponseEntity<List<YoungerStudents>> findYoungerStudents() {
        return ResponseEntity.ok(studentService.findYoungerStudents());
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
        if (studentDTO1 == null) {
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

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузить аватарку студента")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("Файл слишком большой");
        }
        studentAvatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/avatar/preview")
    @Operation(summary = "Посмотреть preview аватарку")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = studentAvatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("/{id}/avatar")
    @Operation(summary = "Посмотреть аватарку")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = studentAvatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }
}
