package org.example.expert.domain.log.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void saveLog(Long managerId, String actions, String status, String message){
        Log log = new Log(managerId, actions, status, message);
        logRepository.save(log);
    }
}
