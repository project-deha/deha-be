package org.deha.be.repository;

import org.deha.be.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlarmRepository extends JpaRepository<Alarm, UUID>, JpaSpecificationExecutor<Alarm> {
    Optional<Alarm> findOneByOwner_IdAndId(UUID ownerId, UUID alarmId);

    List<Alarm> findAllByOwner_Id(UUID ownerId);
}
