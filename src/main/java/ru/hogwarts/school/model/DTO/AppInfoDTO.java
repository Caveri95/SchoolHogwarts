package ru.hogwarts.school.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppInfoDTO {

    private String appName;
    private String appVersion;
    private String appEnvironment;
}
