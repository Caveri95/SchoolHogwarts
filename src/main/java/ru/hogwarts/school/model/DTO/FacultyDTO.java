package ru.hogwarts.school.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.model.Faculty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyDTO {

    private long id;
    private String name;
    private String color;

    public static FacultyDTO mapToFacultyDTO(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setName(faculty.getName());
        facultyDTO.setColor(faculty.getColor());

        return facultyDTO;
    }

    public static Faculty mapToFaculty(FacultyDTO facultyDTO) {
        return new Faculty(facultyDTO.getId(), facultyDTO.getName(), facultyDTO.getColor());
    }
}
