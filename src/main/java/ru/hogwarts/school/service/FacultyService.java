package ru.hogwarts.school.service;

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

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public FacultyDTO createFaculty(FacultyDTO facultyDTO) {
        Faculty faculty = mapToFaculty(facultyDTO);
        return mapToFacultyDTO(facultyRepository.save(faculty));
    }

    public FacultyDTO findFaculty(long id) {
        return mapToFacultyDTO(facultyRepository.getReferenceById(id));
    }

    public FacultyDTO editFaculty(FacultyDTO facultyDTO) {
        Faculty faculty = mapToFaculty(facultyDTO);
        facultyRepository.save(faculty);
        return facultyDTO;
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public List<FacultyDTO> getAllFacultiesOrByName(String name) {
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
        List<Faculty> allByColorIgnoreCase = facultyRepository.findAllByColorIgnoreCase(color);
        List<FacultyDTO> facultyDTOS = new ArrayList<>();

        for (Faculty faculty : allByColorIgnoreCase) {
            FacultyDTO facultyDTO = mapToFacultyDTO(faculty);
            facultyDTOS.add(facultyDTO);
        }
        return facultyDTOS;
    }

    public FacultyDTO getFacultyByIdStudent(long id) {
        Faculty facultyById = facultyRepository.findFacultyById(studentRepository.findById(id).get().getFaculty().getId());
        return mapToFacultyDTO(facultyById);
    }

    public Long getAmountStudents() {
        return facultyRepository.getAmountStudents();
    }

    public List<StudentsByFaculty> getAmountStudentsByFaculty() {
        return facultyRepository.getAmountStudentsByFaculty();
    }






}
