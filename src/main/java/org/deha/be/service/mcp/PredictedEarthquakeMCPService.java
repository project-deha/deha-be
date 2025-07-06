package org.deha.be.service.mcp;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeDto;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import org.deha.be.enums.TurkishCity;
import org.deha.be.service.domain.predictedearthquake.PredictedEarthquakeQueryService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PredictedEarthquakeMCPService {
    private final PredictedEarthquakeQueryService predictedEarthquakeQueryService;

    @Tool(
            name = "getPredictedEarthquakesByCity",
            description = "Get predicted earthquakes for a city with magnitude and date filters."
    )
    public String getPredictedEarthquakesByCity(
            @ToolParam(description = "Minimum magnitude of the earthquake. Optional, if absent sent 0.") float minMagnitude,
            @ToolParam(description = "Maximum magnitude of the earthquake. Optional, if absent sent 10.") float maxMagnitude,
            @ToolParam(description = "Case-insensitive name of the city in Turkish (e.g., 'istanbul', 'Ankara', 'İZmir')") String city,
            @ToolParam(description = "Start date for filtering predictions. Type is Java Instant. Optional, if absent sent today. ") Instant startDate,
            @ToolParam(description = "End date for filtering predictions. Type is Java Instant. Optional, if absent sent end of current year. ") Instant endDate
    ) {
        TurkishCity turkishCity = TurkishCity.valueOf(city.toUpperCase(Locale.of("tr")));

        PredictedEarthquakeFilterDto filter = new PredictedEarthquakeFilterDto(
                minMagnitude, maxMagnitude, turkishCity, startDate, endDate, 0, 10
        );

        Page<PredictedEarthquakeDto> predictedEarthquakes = predictedEarthquakeQueryService.getFiltered(filter);
        if (predictedEarthquakes.isEmpty()) {
            return "No predicted earthquakes found for city: " + city;
        }

        StringBuilder result = new StringBuilder("Predicted earthquakes for city: " + city + "\n");
        predictedEarthquakes.forEach(prediction ->
                result.append("Magnitude: ").append(prediction.magnitude())
                        .append(", Date: ").append(prediction.occurrenceDate())
                        .append("\n")
        );
        return result.toString();
    }
}
