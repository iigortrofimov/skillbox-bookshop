package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.UserDataEditionRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateCleanerServiceImpl {

    private final UserDataEditionRepository userDataEditionRepository;

    @Scheduled(fixedDelayString = "${app.templateCleaner.delay}")
    public void scheduleTask() {
        userDataEditionRepository.findAll().stream()
                .filter(userDataEdition -> userDataEdition.getExpiredTime().isBefore(LocalDateTime.now()))
                .forEach(userDataEditionRepository::delete);
    }
}
