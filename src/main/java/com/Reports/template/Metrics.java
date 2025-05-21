package com.Reports.template;

public enum Metrics {
    MAX_CONCURRENT_CLIENTS_OVER_TIME(1),
    UNIQUE_CLIENTS_OVER_TIME_BY_SSID(2),
    UNIQUE_WIFI_CLIENTS_OVER_TIME(3),
    CLIENT_QUALITY_SCORE_OVER_TIME(4),
    CLIENT_SESSIONS_OVER_TIME(5),
    CLIENT_AIRTIME_USAGE_OVER_TIME(6),
    WIRELESS_CLIENTS_BY_OS(7),
    UNIQUE_CLIENTS_BY_OS(8),
    DEVICES_BY_CLIENTS_OVER_TIME(9),
    SWITCH_SUMMARY(10);

    private final int value;
    Metrics(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    // 3) Static converter method—this must be declared inside the enum
    public static Metrics fromInt(int i) {
        for (Metrics e : Metrics.values()) {
            if (e.value == i) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + i);
    }
}