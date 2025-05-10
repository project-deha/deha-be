package org.deha.be.mapper;

import org.deha.be.dto.user.RegisterRequestParameters;
import org.deha.be.dto.user.UserDto;
import org.deha.be.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);

    @Mapping(target = "isVerified", source = "verified")
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);

    User toEntity(RegisterRequestParameters registerRequestParameters);
}