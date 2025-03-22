package me.utku.dehabe.service.alarm.query;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.alarm.AlarmDto;
import me.utku.dehabe.dto.alarm.AlarmFilterDto;
import me.utku.dehabe.generic.query.ParameterizedQuery;
import me.utku.dehabe.mapper.AlarmMapper;
import me.utku.dehabe.model.Alarm;
import me.utku.dehabe.model.Alarm_;
import me.utku.dehabe.model.Location_;
import me.utku.dehabe.repository.AlarmRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterAlarmQuery implements ParameterizedQuery<List<AlarmDto>, AlarmFilterDto, Alarm> {
    private final AlarmRepository alarmRepository;
    private final AlarmMapper alarmMapper;

    @Override
    public List<AlarmDto> query(AlarmFilterDto alarmFilterDto) {
        Specification<Alarm> alarmSpecification = prepareSpecification(alarmFilterDto);
        List<Alarm> alarms = alarmRepository.findAll(alarmSpecification);
        return alarms.stream().map(alarmMapper::toDto).toList();
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
