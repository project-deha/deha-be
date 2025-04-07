package org.deha.be.service.domain.earthquake;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.earthquake.EarthquakeDto;
import org.deha.be.dto.earthquake.EarthquakeFilterDto;
import org.deha.be.mapper.EarthquakeMapper;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.service.domain.earthquake.query.EarthquakesFilterQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EarthquakeQueryService {
    private final EarthquakesFilterQuery filterEarthquakesQuery;
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
