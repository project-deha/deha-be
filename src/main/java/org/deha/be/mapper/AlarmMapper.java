package org.deha.be.mapper;

import org.deha.be.dto.alarm.AlarmDto;
import org.deha.be.dto.alarm.CreateAlarmDto;
import org.deha.be.model.Alarm;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, LocationMapper.class})
public interface AlarmMapper {
    Alarm toEntity(AlarmDto alarmDto);

    @Mapping(target = "isActive", source = "active")
    AlarmDto toDto(Alarm alarm);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "active", source = "isActive")
    Alarm partialUpdate(AlarmDto alarmDto, @MappingTarget Alarm alarm);

    Alarm toEntity(CreateAlarmDto createAlarmDto);

    List<AlarmDto> toDto(List<Alarm> alarm);
}