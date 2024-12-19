package com.java.attendance.constant;

public enum AttendanceStatus {
    HADIR("HADIR"),
    IZIN("IZIN"),
    SAKIT("SAKIT"),
    TIDAK_HADIR("TIDAK HADIR");

    private String value;

    private AttendanceStatus(String value) {
        this.value = value;
    }

    // get value
    public static AttendanceStatus fromValue(String value) {
        for (AttendanceStatus status : AttendanceStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
