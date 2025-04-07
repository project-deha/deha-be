package org.deha.be.mapper;

import org.deha.be.dto.predictedearthquake.PredictedEarthquakeDto;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocationMapper.class})
public interface PredictedEarthquakeMapper {
    PredictedEarthquake toEntity(PredictedEarthquakeDto predictedEarthquakeDto);

    PredictedEarthquakeDto toDto(PredictedEarthquake predictedEarthquake);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PredictedEarthquake partialUpdate(PredictedEarthquakeDto predictedEarthquakeDto, @MappingTarget PredictedEarthquake predictedEarthquake);
}