package com.java.attendance.specification;

import com.java.attendance.constant.AttendanceStatus;
import com.java.attendance.dto.request.SearchAttendanceRequest;
import com.java.attendance.entity.Attendance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AttendanceSpecification {
    public static Specification<Attendance> getSpecification(SearchAttendanceRequest searchAttendanceRequest) {
        return new Specification<Attendance>() {
            @Override
            public Predicate toPredicate(Root<Attendance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.hasText(searchAttendanceRequest.getEmployeeName())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeName")), "%" + searchAttendanceRequest.getEmployeeName().toLowerCase() + "%" ));
                }

                if (StringUtils.hasText(searchAttendanceRequest.getStatus())) {
                    try {
                        predicates.add(criteriaBuilder.equal(root.get("status"), AttendanceStatus.fromValue(searchAttendanceRequest.getStatus())));
                    } catch (IllegalArgumentException ex) {
                        throw new IllegalArgumentException("Invalid status: " + searchAttendanceRequest.getStatus());
                    }
                }

                if (!predicates.isEmpty()) {
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
