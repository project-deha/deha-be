package org.deha.be.mapper;

import org.deha.be.dto.alarm.AlarmDto;
import org.deha.be.model.Alarm;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, LocationMapper.class})
public interface AlarmMapper {
    Alarm toEntity(AlarmDto alarmDto);

    AlarmDto toDto(Alarm alarm);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Alarm partialUpdate(AlarmDto alarmDto, @MappingTarget Alarm alarm);
}