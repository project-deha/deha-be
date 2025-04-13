package org.deha.be.service.domain.emailverification;

import org.deha.be.dto.user.UserDto;
import org.deha.be.enums.Role;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.EmailVerification;
import org.deha.be.repository.EmailVerificationRepository;
import org.deha.be.service.domain.emailverification.command.CreateEmailVerificationCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateEmailVerificationCommandTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    private CreateEmailVerificationCommand createEmailVerificationCommand;

    @Test
    public void testCreateEmailVerification() {
        createEmailVerificationCommand = new CreateEmailVerificationCommand(emailVerificationRepository, userMapper);

        UserDto userDto = new UserDto(
                UUID.randomUUID(),
                "damlaiyiis@example.com",
                "Damla",
                "Iyiis",
                Set.of(Role.ROLE_RESEARCHER),
                true
        );

        Mockito.when(emailVerificationRepository.save(Mockito.any(EmailVerification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmailVerification result = createEmailVerificationCommand.execute(userDto);
        result.prePersist();

        Assertions.assertEquals(result.getUser().getEmail(), userDto.email());
        Assertions.assertNotNull(result.getCode());
        Assertions.assertNotNull(result.getExpireAt());
    }
}
