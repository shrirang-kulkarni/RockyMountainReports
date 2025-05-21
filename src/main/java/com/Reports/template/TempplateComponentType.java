package com.Reports.template;

public enum TempplateComponentType {
    TEMP_COMP_TYPE_TABLE(1),
    TEMP_COMP_TYPE_BAR_CHART(2),
    TEMP_COMP_TYPE_PIE_CHART(3),
    TEMP_COMP_TYPE_LINE_GRAPH(4);

    private final int value;

    TempplateComponentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    // 3) Static converter method—this must be declared inside the enum
    public static TempplateComponentType fromInt(int i) {
        for (TempplateComponentType e : TempplateComponentType.values()) {
            if (e.value == i) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + i);
    }
};
