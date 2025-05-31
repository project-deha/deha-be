package org.deha.be.service.domain.user.command;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.User;
import org.deha.be.repository.UserRepository;
import org.deha.be.service.email.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResetPasswordCommand implements EffectCommand<String> {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Override
    public void execute(String s) {
        User user = userRepository.findByEmail(s).orElseThrow(EntityNotFoundException::new);
        String newPassword = randomPasswordGenerator();
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        emailService.sendResetPasswordEmail(user, newPassword);
    }

    private String randomPasswordGenerator() {
        String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUMBERS = "0123456789";
        String SPECIALS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";
        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();

        passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordChars.add(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        passwordChars.add(SPECIALS.charAt(random.nextInt(SPECIALS.length())));

        String allChars = LOWERCASE + UPPERCASE + NUMBERS + SPECIALS;
        for (int i = 0; i < 8; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        Collections.shuffle(passwordChars, random);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
