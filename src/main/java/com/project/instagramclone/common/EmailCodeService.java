package com.project.instagramclone.common;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
@RequiredArgsConstructor
public class EmailCodeService {
    private final JavaMailSender 메일발송기;
    private final Map<String, 코드정보> 보관함 = new ConcurrentHashMap<>();
    public void 인증번호발송(String 받는이메일) {
        String 코드 = String.format("%06d", new Random().nextInt(999999));
        보관함.put(받는이메일, new 코드정보(코드, LocalDateTime.now().plusMinutes(5)));

        SimpleMailMessage 메세지 = new SimpleMailMessage();
        메세지.setTo(받는이메일);
        메세지.setSubject("[SpringBoard] 이메일 인증번호 : ");
        메세지.setText("인증번호 : " + 코드 + "\n\n5분 안에 입력해주세요.");
        메일발송기.send(메세지);
    }
    public boolean 인증번호확인(String 이메일, String 입력코드) {
        코드정보 저장된정보 = 보관함.get(이메일);
        if(저장된정보 == null)  return  false;
        if(저장된정보.만료시각().isBefore(LocalDateTime.now())) {
            보관함.remove(이메일);
            return  false;
        }
        if(!저장된정보.코드().equals(입력코드)) return false;
        보관함.remove(이메일);
        return  true;
    }
    private record 코드정보(String 코드, LocalDateTime 만료시각) {}

}









