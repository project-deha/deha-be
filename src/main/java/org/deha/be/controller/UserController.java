package org.deha.be.controller;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.user.ChangePasswordParameters;
import org.deha.be.dto.user.UpdateUserRequestParameters;
import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.GenericResponse;
import org.deha.be.model.User;
import org.deha.be.service.domain.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/me")
    @PreAuthorize("hasRole('RESEARCHER')")
    public GenericResponse<UserDto> updateUser(@AuthenticationPrincipal User user, @RequestBody UserDto updateData) {
        UserDto updatedUser = userService.updateUser(new UpdateUserRequestParameters(updateData, user));
        return GenericResponse.ok(updatedUser);
    }

    @PatchMapping("/me/password")
    @PreAuthorize("hasRole('RESEARCHER')")
    public GenericResponse<UserDto> changePassword(@AuthenticationPrincipal User user, @RequestParam String password,
                                                   @RequestParam String newPassword) {
        UserDto changedUser = userService.changePassword(new ChangePasswordParameters(user, password, newPassword));
        return GenericResponse.ok(changedUser);
    }
}
