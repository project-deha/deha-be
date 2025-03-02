package me.utku.dehabe.service.earthquake;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.earthquake.EarthquakeDto;
import me.utku.dehabe.dto.earthquake.EarthquakeFilterDto;
import me.utku.dehabe.mapper.EarthquakeMapper;
import me.utku.dehabe.model.earthquakes.Earthquake;
import me.utku.dehabe.service.earthquake.query.FilterEarthquakesQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EarthquakeQueryService {
    private final FilterEarthquakesQuery filterEarthquakesQuery;
    private final EarthquakeMapper earthquakeMapper;

    public Page<EarthquakeDto> getFilteredEarthquakes(EarthquakeFilterDto earthquakeFilter) {
        Page<Earthquake> earthquakes = filterEarthquakesQuery.query(earthquakeFilter);
        return earthquakeMapper.toDtoPage(earthquakes);
    }
}
