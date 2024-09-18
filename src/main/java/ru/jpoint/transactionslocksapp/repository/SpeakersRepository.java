package ru.jpoint.transactionslocksapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import ru.jpoint.transactionslocksapp.entities.SpeakerEntity;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface SpeakersRepository extends JpaRepository<SpeakerEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // added because of concurrent calls
    Optional<SpeakerEntity> findByTalkName(String talkName);

}
