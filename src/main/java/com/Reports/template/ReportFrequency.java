package com.Reports.template;

public enum ReportFrequency {
    HOURLY(0),
    DAILY(1),
    WEEKLY(2),
    MONTHLY(3),
    YEARLY(4),
    ONDEMAND(5);


    private final int frequency;

    ReportFrequency(int code) {
        this.frequency = code;
    }

    public int getCode() {
        return frequency;
    }

    public static ReportFrequency fromCode(int code) {
        for (ReportFrequency freq : ReportFrequency.values()) {
            if (freq.getCode() == code) {
                return freq;
            }
        }
        throw new IllegalArgumentException("Invalid frequency code: " + code);
    }
}
