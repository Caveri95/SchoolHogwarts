package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.DTO.FacultyDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public FacultyDTO createFaculty(Faculty faculty) {
        return mapToFacultyDTO(facultyRepository.save(faculty));
    }

    public FacultyDTO findFaculty(long id) {
        return mapToFacultyDTO(facultyRepository.getReferenceById(id));
    }

    public FacultyDTO editFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        return mapToFacultyDTO(faculty);
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

    private FacultyDTO mapToFacultyDTO(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setName(faculty.getName());
        facultyDTO.setColor(faculty.getColor());

        return facultyDTO;
    }

    private Faculty mapToFaculty(FacultyDTO facultyDTO) {
        return new Faculty(facultyDTO.getId(), facultyDTO.getName(), facultyDTO.getColor());
    }


}
