package org.deha.be.mapper;

import org.deha.be.dto.report.ReportDto;
import org.deha.be.model.Report;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {
    Report toEntity(ReportDto reportDto);

    ReportDto toDto(Report report);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Report partialUpdate(ReportDto reportDto, @MappingTarget Report report);
}