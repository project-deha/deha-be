package org.deha.be.controller;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AlarmDto;
import org.deha.be.dto.alarm.CreateAlarmDto;
import org.deha.be.generic.GenericResponse;
import org.deha.be.model.User;
import org.deha.be.service.domain.alarm.AlarmService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @PostMapping
    @PreAuthorize("hasRole('RESEARCHER')")
    public GenericResponse<AlarmDto> createAlarm(@AuthenticationPrincipal User user, @RequestBody CreateAlarmDto createAlarmDto) {
        AlarmDto createdAlarm = alarmService.createAlarm(user, createAlarmDto);
        return GenericResponse.ok(createdAlarm);
    }
}
