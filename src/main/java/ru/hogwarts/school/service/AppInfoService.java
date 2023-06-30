package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.DTO.AppInfoDTO;

@Service
@Profile("!test")

public class AppInfoService implements Info {

    @Value ("${app.env}")
    private String appEnv;

    public AppInfoDTO getAppInfo() {
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        appInfoDTO.setAppName("hogwarts-school");
        appInfoDTO.setAppVersion("0.0.1");
        appInfoDTO.setAppEnvironment(appEnv);
        return appInfoDTO;
    }


}
