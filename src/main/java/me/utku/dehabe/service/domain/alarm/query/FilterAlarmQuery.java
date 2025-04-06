package me.utku.dehabe.service.domain.alarm.query;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.alarm.AlarmFilterDto;
import me.utku.dehabe.generic.query.ParameterizedQuery;
import me.utku.dehabe.model.Alarm;
import me.utku.dehabe.model.Alarm_;
import me.utku.dehabe.model.Location_;
import me.utku.dehabe.repository.AlarmRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterAlarmQuery implements ParameterizedQuery<List<Alarm>, AlarmFilterDto, Alarm> {
    private final AlarmRepository alarmRepository;

    @Override
    public List<Alarm> query(AlarmFilterDto alarmFilterDto) {
        Specification<Alarm> alarmSpecification = prepareSpecification(alarmFilterDto);
        return alarmRepository.findAll(alarmSpecification);
    }

    @Override
    public Specification<Alarm> prepareSpecification(AlarmFilterDto alarmFilterDto) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(Alarm_.minimumMagnitude), alarmFilterDto.magnitude()),
                        criteriaBuilder.lessThanOrEqualTo(root.get(Alarm_.maximumMagnitude), alarmFilterDto.magnitude()),
                        criteriaBuilder.equal(root.join(Alarm_.location).get(Location_.city), alarmFilterDto.city())
                );
    }
}
