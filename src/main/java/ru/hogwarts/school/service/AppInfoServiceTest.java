package ru.hogwarts.school.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.DTO.AppInfoDTO;

@Service
@Profile("test")
public class AppInfoServiceTest implements Info{

    public AppInfoDTO getAppInfo() {
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        appInfoDTO.setAppName("hogwarts-school");
        appInfoDTO.setAppVersion("0.0.1");
        appInfoDTO.setAppEnvironment("noTest");
        return appInfoDTO;
    }
}
