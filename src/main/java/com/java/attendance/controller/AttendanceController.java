package com.java.attendance.controller;

import com.java.attendance.constant.Constant;
import com.java.attendance.dto.request.AttendanceRequest;
import com.java.attendance.dto.request.SearchAttendanceRequest;
import com.java.attendance.dto.response.AttendanceResponse;
import com.java.attendance.service.AttendanceService;
import com.java.attendance.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.ATTENDANCE_API)
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AttendanceRequest attendanceRequest) {
        AttendanceResponse attendanceResponse = attendanceService.save(attendanceRequest);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_ATTENDANCE, attendanceResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody AttendanceRequest attendanceRequest) {
        AttendanceResponse attendanceResponse = attendanceService.update(id, attendanceRequest);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_ATTENDANCE, attendanceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        attendanceService.delete(id);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_DELETE_ATTENDANCE, null);
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "employeeName", required = false) String employeeName,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sort", defaultValue = "employeeName") String sort
    ) {
        SearchAttendanceRequest searchAttendanceRequest = SearchAttendanceRequest.builder()
                .employeeName(employeeName)
                .status(status)
                .page(page)
                .size(size)
                .sort(sort)
                .build();
        Page<AttendanceResponse> attendanceResponses = attendanceService.getAll(searchAttendanceRequest);

        return ResponseUtil.buildPageResponse(HttpStatus.OK, Constant.SUCCESS_GET_ALL_ATTENDANCE, attendanceResponses);
    }
}
