package com.Reports.engine;

public enum ReportComponentType {
    REPORT_COMP_TYPE_TABLE(1),
    REPORT_COMP_TYPE_BAR_CHART(2),
    REPORT_COMP_TYPE_PIE_CHART(3),
    REPORT_COMP_TYPE_LINE_GRAPH(4);

    private final int value;

    ReportComponentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    // 3) Static converter method—this must be declared inside the enum
    public static ReportComponentType fromInt(int i) {
        for (ReportComponentType e : ReportComponentType.values()) {
            if (e.value == i) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + i);
    }
};
