package com.java.attendance.service.impl;

import com.java.attendance.constant.AttendanceStatus;
import com.java.attendance.constant.Constant;
import com.java.attendance.dto.request.AttendanceRequest;
import com.java.attendance.dto.request.SearchAttendanceRequest;
import com.java.attendance.dto.response.AttendanceResponse;
import com.java.attendance.entity.Attendance;
import com.java.attendance.repository.AttendanceRepository;
import com.java.attendance.service.AttendanceService;
import com.java.attendance.specification.AttendanceSpecification;
import com.java.attendance.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Override
    public AttendanceResponse save(AttendanceRequest attendanceRequest) {
        Attendance attendance = Attendance.builder()
                .employeeName(attendanceRequest.getEmployeeName())
                .date(attendanceRequest.getDate())
                .status(AttendanceStatus.fromValue(attendanceRequest.getStatus()))
                .build();
        attendanceRepository.saveAndFlush(attendance);
        return toAttendanceResponse(attendance);
    }

    @Override
    public AttendanceResponse update(String id, AttendanceRequest attendanceRequest) {
        Attendance currentAttendance = getOne(id);

        currentAttendance.setId(id);
        currentAttendance.setEmployeeName(attendanceRequest.getEmployeeName());
        currentAttendance.setDate(attendanceRequest.getDate());
        currentAttendance.setStatus(AttendanceStatus.fromValue(attendanceRequest.getStatus()));
        attendanceRepository.save(currentAttendance);
        return toAttendanceResponse(currentAttendance);
    }

    @Override
    public AttendanceResponse findById(String id) {
        Attendance attendance = getOne(id);

        return toAttendanceResponse(attendance);
    }

    @Override
    public Page<AttendanceResponse> getAll(SearchAttendanceRequest searchAttendanceRequest) {
        Pageable attendancePageable = PageRequest.of(
                (searchAttendanceRequest.getPage() - 1),
                searchAttendanceRequest.getSize(),
                SortUtil.parseSortFromQueryParam(searchAttendanceRequest.getSort())
        );
        Specification<Attendance> attendanceSpecification = AttendanceSpecification.getSpecification(searchAttendanceRequest);
        Page<Attendance> attendancePage = attendanceRepository.findAll(attendanceSpecification, attendancePageable);
        return attendancePage.map(attendance -> toAttendanceResponse(attendance));
    }

    @Override
    public void delete(String id) {
        Attendance attendance = getOne(id);

        attendanceRepository.delete(attendance);
    }

    @Override
    public Attendance getOne(String id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_ATTENDANCE_NOT_FOUND));
    }

    private AttendanceResponse toAttendanceResponse(Attendance attendance) {
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        attendanceResponse.setId(attendance.getId());
        attendanceResponse.setEmployeeName(attendance.getEmployeeName());
        attendanceResponse.setDate(attendance.getDate().toString());
        attendanceResponse.setStatus(attendance.getStatus().toString());
        return attendanceResponse;
    }
}
