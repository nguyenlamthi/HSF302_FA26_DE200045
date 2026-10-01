package com.hsf302.ch4.dto;

public record CourseStatDTO(
        String code,
        String name,
        int capacity,
        long enrolled,
        Double avgGpa
) {
    /** Tính số chỗ còn trống */
    public int remaining() {
        return (int) Math.max(0, capacity - enrolled);
    }

    @Override
    public String toString() {
        String gpaStr = (avgGpa == null) ? "null" : String.format("%.3f", avgGpa);
        return String.format("%s | %-40s | %d/%d (con %d) | avg GPA: %s",
                code, name, enrolled, capacity, remaining(), gpaStr);
    }
}