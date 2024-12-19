package com.java.attendance.service;

import com.java.attendance.dto.request.AttendanceRequest;
import com.java.attendance.dto.request.SearchAttendanceRequest;
import com.java.attendance.dto.response.AttendanceResponse;
import com.java.attendance.entity.Attendance;
import org.springframework.data.domain.Page;

public interface AttendanceService {
    AttendanceResponse save(AttendanceRequest attendanceRequest);
    AttendanceResponse update(String id, AttendanceRequest attendanceRequest);
    AttendanceResponse findById(String id);
    Attendance getOne(String id);
    Page<AttendanceResponse> getAll(SearchAttendanceRequest searchAttendanceRequest);
    void delete(String id);
}
