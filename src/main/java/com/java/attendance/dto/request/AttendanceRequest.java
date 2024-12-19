package com.java.attendance.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRequest {
    private String employeeName;
    private LocalDate date;
    private String status;
}
