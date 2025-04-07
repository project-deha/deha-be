package org.deha.be.mapper;

import org.deha.be.dto.alarm.AFADApiResponse;
import org.deha.be.dto.earthquake.EarthquakeDto;
import org.deha.be.enums.TurkishCity;
import org.deha.be.model.Location;
import org.deha.be.model.earthquakes.Earthquake;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    @Named("localDateTimeToInstant")
    default Instant toInstant(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.toInstant(ZoneOffset.ofHours(-3));
    }

    @Mapping(target = "location", expression = "java(toLocation(earthquakeResponseDto.province(), earthquakeResponseDto.latitude(), earthquakeResponseDto.longitude()))")
    @Mapping(target = "occurrenceDate", expression = "java(toInstant(earthquakeResponseDto.date()))")
    Earthquake toEntity(AFADApiResponse earthquakeResponseDto);
}
