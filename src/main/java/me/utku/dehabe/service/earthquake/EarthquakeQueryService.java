package me.utku.dehabe.service.earthquake;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.earthquake.EarthquakeDto;
import me.utku.dehabe.dto.earthquake.EarthquakeFilterDto;
import me.utku.dehabe.mapper.EarthquakeMapper;
import me.utku.dehabe.model.earthquakes.Earthquake;
import me.utku.dehabe.service.earthquake.query.FilterEarthquakesFilterQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EarthquakeQueryService {
    private final FilterEarthquakesFilterQuery filterEarthquakesQuery;
    private final EarthquakeMapper earthquakeMapper;

    /**
     * @param earthquakeFilter filter object to filter earthquakes.
     * @return Paginated filtered earthquakes.
     */
    public Page<EarthquakeDto> getFiltered(EarthquakeFilterDto earthquakeFilter) {
        Page<Earthquake> earthquakes = filterEarthquakesQuery.query(earthquakeFilter);
        return earthquakes.map(earthquakeMapper::toDto);
    }
}
