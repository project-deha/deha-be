package me.utku.dehabe.mapper;

import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeDto;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocationMapper.class})
public interface PredictedEarthquakeMapper {
    PredictedEarthquake toEntity(PredictedEarthquakeDto predictedEarthquakeDto);

    PredictedEarthquakeDto toDto(PredictedEarthquake predictedEarthquake);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PredictedEarthquake partialUpdate(PredictedEarthquakeDto predictedEarthquakeDto, @MappingTarget PredictedEarthquake predictedEarthquake);
}