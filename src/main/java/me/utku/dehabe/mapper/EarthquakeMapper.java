package me.utku.dehabe.mapper;

import me.utku.dehabe.dto.alarm.AfadEarthquakeApiResponse;
import me.utku.dehabe.dto.earthquake.EarthquakeDto;
import me.utku.dehabe.enums.TurkishCity;
import me.utku.dehabe.model.Location;
import me.utku.dehabe.model.earthquakes.Earthquake;
import org.mapstruct.*;

import java.util.Locale;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocationMapper.class})
public interface EarthquakeMapper {
    Earthquake toEntity(EarthquakeDto earthquakeDto);

    EarthquakeDto toDto(Earthquake earthquake);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Earthquake partialUpdate(EarthquakeDto earthquakeDto, @MappingTarget Earthquake earthquake);

    @Named("provinceToLocation")
    default Location toLocation(String province, float latitude, float longitude) {
        Location location = new Location();
        location.setCity(TurkishCity.valueOf(province.toUpperCase(Locale.forLanguageTag("tr"))));
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    @Mapping(target = "location", expression = "java(toLocation(earthquakeResponseDto.province(), earthquakeResponseDto.latitude(), earthquakeResponseDto.longitude()))")
    Earthquake toEntity(AfadEarthquakeApiResponse earthquakeResponseDto);
}
