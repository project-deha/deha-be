package org.deha.be.service.domain.user.command;

import org.deha.be.enums.Role;
import org.deha.be.model.User;
import org.deha.be.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VerifyUserCommandTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VerifyUserCommand verifyUserCommand;

    User user = new User();

    @Test
    public void testVerifyUser() {
        user.setFirstName("Ahmet");
        user.setLastName("Yilmaz");
        user.setEmail("ahmetyilmaz@gmail.com");
        user.setVerified(false);
        user.setAuthorities(new HashSet<>(List.of(Role.ROLE_UNVALIDATED_RESEARCHER)));

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        User result = verifyUserCommand.execute(user);

        Assertions.assertEquals(Boolean.TRUE, result.isVerified());
        Assertions.assertTrue(user.getAuthorities().contains(Role.ROLE_RESEARCHER));
        Assertions.assertFalse(user.getAuthorities().contains(Role.ROLE_UNVALIDATED_RESEARCHER));
    }
}
