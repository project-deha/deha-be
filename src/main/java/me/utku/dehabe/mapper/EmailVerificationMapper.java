package me.utku.dehabe.mapper;

import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.model.EmailVerification;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface EmailVerificationMapper {
    EmailVerification toEntity(EmailVerificationDto emailVerificationDto);

    EmailVerificationDto toDto(EmailVerification emailVerification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EmailVerification partialUpdate(EmailVerificationDto emailVerificationDto, @MappingTarget EmailVerification emailVerification);
}