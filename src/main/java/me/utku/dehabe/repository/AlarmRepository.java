package me.utku.dehabe.repository;

import me.utku.dehabe.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AlarmRepository extends JpaRepository<Alarm, UUID>, JpaSpecificationExecutor<Alarm> {

}
