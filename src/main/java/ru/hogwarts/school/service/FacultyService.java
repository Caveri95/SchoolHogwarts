package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.DTO.FacultyDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.interfaceForRequest.StudentsByFaculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.hogwarts.school.model.DTO.FacultyDTO.mapToFaculty;
import static ru.hogwarts.school.model.DTO.FacultyDTO.mapToFacultyDTO;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public FacultyDTO createFaculty(FacultyDTO facultyDTO) {
        logger.info("Вызов метода для создания факультета {}", facultyDTO);
        Faculty faculty = mapToFaculty(facultyDTO);
        return mapToFacultyDTO(facultyRepository.save(faculty));
    }

    public FacultyDTO findFaculty(long id) {
        logger.info("Вызов метода для поиска факультета по его id = {}", id);
        return mapToFacultyDTO(facultyRepository.getReferenceById(id));
    }

    public FacultyDTO editFaculty(FacultyDTO facultyDTO) {
        logger.info("Вызов метода для редактирования факультета {}", facultyDTO);
        Faculty faculty = mapToFaculty(facultyDTO);
        facultyRepository.save(faculty);
        return facultyDTO;
    }

    public void deleteFaculty(long id) {
        logger.info("Вызов метода для удаления факультета по его id = {}", id);
        facultyRepository.deleteById(id);
    }

    public List<FacultyDTO> getAllFacultiesOrByName(String name) {
        logger.info("Вызов метода для получения всех факультетов или вывода факультетов по имени - {}", name);
        if (name != null && !name.isBlank()) {
            List<Faculty> facultiesByNameIgnoreCase = facultyRepository.findFacultiesByNameIgnoreCase(name);
            List<FacultyDTO> facultyDTOS = new ArrayList<>();

            for (Faculty faculty : facultiesByNameIgnoreCase) {
                FacultyDTO facultyDTO = mapToFacultyDTO(faculty);
                facultyDTOS.add(facultyDTO);
            }
            return facultyDTOS;
        } else {
            List<Faculty> faculties = facultyRepository.findAll();
            List<FacultyDTO> facultyDTOS = new ArrayList<>();
            for (Faculty faculty : faculties) {
                FacultyDTO facultyDTO = mapToFacultyDTO(faculty);
                facultyDTOS.add(facultyDTO);
            }
            return facultyDTOS;
        }
    }

    public List<FacultyDTO> getFacultyByColor(String color) {
        logger.info("Вызов метода для получения факультета по его цвету - {}", color);
        List<Faculty> allByColorIgnoreCase = facultyRepository.findAllByColorIgnoreCase(color);
        List<FacultyDTO> facultyDTOS = new ArrayList<>();

        for (Faculty faculty : allByColorIgnoreCase) {
            FacultyDTO facultyDTO = mapToFacultyDTO(faculty);
            facultyDTOS.add(facultyDTO);
        }
        return facultyDTOS;
    }

    public FacultyDTO getFacultyByIdStudent(long id) {
        logger.info("Вызов метода для получения факультета по id студента. Id - {}", id);
        Faculty facultyById = facultyRepository.findFacultyById(studentRepository.findById(id).get().getFaculty().getId());
        return mapToFacultyDTO(facultyById);
    }

    public Long getAmountStudents() {
        logger.info("Вызов метода для вывода общего количества студентов в школе");
        return facultyRepository.getAmountStudents();
    }

    public List<StudentsByFaculty> getAmountStudentsByFaculty() {
        logger.info("Вызов метода для вывода количества студентов по факультетам");
        return facultyRepository.getAmountStudentsByFaculty();
    }






}
