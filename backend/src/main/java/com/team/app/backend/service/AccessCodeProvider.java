package com.team.app.backend.service;

import com.team.app.backend.persistance.model.Quiz;
import com.team.app.backend.persistance.model.Session;
import org.jasypt.util.text.BasicTextEncryptor;

import java.util.HashMap;
import java.util.Map;

public class AccessCodeProvider {
    static private BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();


    public static String createAccessCode(Session session, Quiz quiz) {
        basicTextEncryptor.setPassword("12345");
        String link = "?quiz_id=" + quiz.getId() + "&session_id=" + session.getId();
        String encr=basicTextEncryptor.encrypt(link);



        return basicTextEncryptor.encrypt(link);
    }

    public static Map<String, Long> parseAccessCode(String accessCode) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword("12345");


        Map<String, Long> map = new HashMap<>();
        String[] values =
                accessCode
                .replace("?quiz_id=", "")
                .replace("session_id=", "")
                .split("&");
        map.put("quiz_id", Long.parseLong(values[0]));
        map.put("session_id", Long.parseLong(values[1]));
        return map;
    }

}
