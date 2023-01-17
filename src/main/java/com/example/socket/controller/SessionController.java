package com.example.socket.controller;

import com.example.socket.service.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("")
public class SessionController {
    private final SessionService service;

    @GetMapping
    public String loginForm() {
        return "loginForm";
    }

    /**
     * 세션을 db에 저장 후 쿠키 생성
     *
     * @param loginId 생성할 세션의 loginId
     * @param response 쿠키를 저장할 Response
     * @return main창으로 이동
     * @author : 김선홍
     */
    @PostMapping("/login")
    public String login(@RequestParam String loginId, HttpServletResponse response) {
        String uuid = service.saveSession(loginId);
        Cookie sessionCookie = new Cookie("customSessionID", uuid);
        response.addCookie(sessionCookie);
        return "main";
    }

    /**
     * 로그아웃하면 세션과 쿠키를 삭제
     *
     * @param request 삭제할 쿠키를 꺼낼 request
     * @return 로그아웃 후 이동 장소
     * @author : 김선홍
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, "customSessionID");
        if (sessionCookie != null) {
            service.deleteSession(sessionCookie.getValue());
            return "logout";
        }
        return "main";
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
