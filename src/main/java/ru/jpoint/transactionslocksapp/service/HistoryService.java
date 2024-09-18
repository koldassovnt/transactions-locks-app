package ru.jpoint.transactionslocksapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jpoint.transactionslocksapp.dto.Likes;
import ru.jpoint.transactionslocksapp.entities.HistoryEntity;
import ru.jpoint.transactionslocksapp.repository.HistoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    /**
     * Method for saving message to history.
     * Produces the message with DTO to kafka, for future processing.
     *
     * @param likes DTO with information about likes to be added.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 2) // created new transaction so that history will be saved anyway
    public void saveMessageToHistory(Likes likes, String status) {
        try {
            historyRepository.save(HistoryEntity.builder()
                    .talkName(likes.getTalkName())
                    .likes(likes.getLikes())
                    .status(status)
                    .build());

        } catch (RuntimeException ex) {
            log.warn("Failed to save message to history.", ex);
        }
    }
}
