package me.utku.dehabe.mapper;

import me.utku.dehabe.dto.earthquake.EarthquakeDto;
import me.utku.dehabe.model.earthquakes.Earthquake;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocationMapper.class})
public interface EarthquakeMapper {
    Earthquake toEntity(EarthquakeDto earthquakeDto);

    EarthquakeDto toDto(Earthquake earthquake);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Earthquake partialUpdate(EarthquakeDto earthquakeDto, @MappingTarget Earthquake earthquake);
}
