package com.java.attendance.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceResponse {
    private String id;
    private String employeeName;
    private String date;
    private String status;
}
