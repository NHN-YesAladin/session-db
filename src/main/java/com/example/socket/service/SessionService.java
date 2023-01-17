package com.example.socket.service;

import com.example.socket.entity.SessionEntity;
import com.example.socket.repository.JpaSessionRepository;
import com.example.socket.session.CustomHttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final JpaSessionRepository sessionRepository;
    private final CustomHttpSession customHttpSession;

    /**
     * 세션을 생성 후 db에 저장
     *
     * @param loginId 세션을 생성할 loginId
     * @return 세션의 uuid
     * @author : 김선홍
     */
    @Transactional
    public String saveSession(String loginId) {
        customHttpSession.setAttribute("loginId", loginId);
        sessionRepository.save(new SessionEntity(
                customHttpSession.getId(),
                customHttpSession.getCreationTime(),
                customHttpSession.getLastAccessedTime(),
                customHttpSession.getMaxInactiveInterval(),
                customHttpSession.getAttributes(),
                customHttpSession.isNew()
        ));
        return customHttpSession.getId();
    }

    /**
     * 세션을 삭제한다
     *
     * @param loginId 세션을 삭제할 loginId
     * @author : 김선홍
     */
    @Transactional
    public void deleteSession(String loginId) {
        customHttpSession.invalidate();
        sessionRepository.deleteById(loginId);
    }
}
