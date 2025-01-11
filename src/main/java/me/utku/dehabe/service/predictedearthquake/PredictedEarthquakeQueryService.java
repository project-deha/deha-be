package me.utku.dehabe.service.predictedearthquake;

import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeDto;
import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import me.utku.dehabe.mapper.PredictedEarthquakeMapper;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import me.utku.dehabe.repository.PredictedEarthquakeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PredictedEarthquakeQueryService {
    private final PredictedEarthquakeRepository predictedEarthquakeRepository;
    private final PredictedEarthquakeMapper predictedEarthquakeMapper;

    public PredictedEarthquakeQueryService(PredictedEarthquakeRepository predictedEarthquakeRepository, PredictedEarthquakeMapper predictedEarthquakeMapper) {
        this.predictedEarthquakeRepository = predictedEarthquakeRepository;
        this.predictedEarthquakeMapper = predictedEarthquakeMapper;
    }

    public List<PredictedEarthquakeDto> getAll() {
        List<PredictedEarthquake> predictions = predictedEarthquakeRepository.findAll();
        return predictions.stream().map(predictedEarthquakeMapper::toDto).toList();
    }

    public Page<PredictedEarthquake> getAllFiltered(PredictedEarthquakeFilterDto filterDto) {
        Instant startDate = Optional.ofNullable(filterDto.startDate()).orElse(Instant.now()
                .minus(5, ChronoUnit.DAYS));
        Instant endDate = Optional.ofNullable(filterDto.startDate())
                .orElse(Instant.now())
                .atZone(ZoneId.systemDefault())
                .plusMonths(12)
                .toInstant();

        float minMagnitude = filterDto.minMagnitude() == 0F ? 1F : filterDto.minMagnitude();
        float maxMagnitude = filterDto.maxMagnitude() == 0F ? 10F : filterDto.maxMagnitude();


        List<String> CITIES = Arrays.asList(
                "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya",
                "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir",
                "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur",
                "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli",
                "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum",
                "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari",
                "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir",
                "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir",
                "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa",
                "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir",
                "Niğde", "Ordu", "Rize", "Sakarya", "Samsun",
                "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat",
                "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van",
                "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman",
                "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan",
                "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye",
                "Düzce"
        );

        List<String> cityList;

        if (filterDto.city() == null) {
            cityList = CITIES;
        } else {
            cityList = Arrays.asList(filterDto.city());
        }

        Page<PredictedEarthquake> predictions = predictedEarthquakeRepository
                .findAllByMagnitudeIsBetweenAndLocation_CityInAndPredictionDateIsBetween(
                        minMagnitude, maxMagnitude,
                        cityList,
                        startDate, endDate,
                        PageRequest.of(filterDto.page(), filterDto.size())
                );
        return predictions;
    }
}
