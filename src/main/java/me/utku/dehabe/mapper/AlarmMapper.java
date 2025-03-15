package me.utku.dehabe.mapper;

import me.utku.dehabe.model.Alarm;
import me.utku.dehabe.model.AlarmDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, LocationMapper.class})
public interface AlarmMapper {
    Alarm toEntity(AlarmDto alarmDto);

    AlarmDto toDto(Alarm alarm);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Alarm partialUpdate(AlarmDto alarmDto, @MappingTarget Alarm alarm);
}