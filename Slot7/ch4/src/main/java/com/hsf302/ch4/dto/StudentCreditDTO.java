package com.hsf302.ch4.dto;

public record StudentCreditDTO(
        String studentCode,
        String fullName,
        long courseCount,
        long totalCredits
) {
    @Override
    public String toString() {
        return String.format("%s | %-15s | %d khoa | %d tin chi",
                studentCode, fullName, courseCount, totalCredits);
    }
}