package org.deha.be.controller;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AlarmDto;
import org.deha.be.dto.alarm.CreateAlarmDto;
import org.deha.be.generic.GenericResponse;
import org.deha.be.model.User;
import org.deha.be.service.domain.alarm.AlarmQueryService;
import org.deha.be.service.domain.alarm.AlarmService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
    private final AlarmQueryService alarmQueryService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('RESEARCHER')")
    public List<AlarmDto> getUserAlarm(@AuthenticationPrincipal User user) {
        return alarmQueryService.getUserAlarms(user);
    }

    @PostMapping
    @PreAuthorize("hasRole('RESEARCHER')")
    public GenericResponse<List<AlarmDto>> createAlarm(@AuthenticationPrincipal User user, @RequestBody CreateAlarmDto createAlarmDto) {
        List<AlarmDto> createdAlarm = alarmService.createAlarms(user, createAlarmDto);
        return GenericResponse.ok(createdAlarm);
    }
}
