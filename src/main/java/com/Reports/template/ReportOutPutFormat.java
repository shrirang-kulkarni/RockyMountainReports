package com.Reports.template;

public enum ReportOutPutFormat {
    PDF(0),
    HTML(1);

    private final int format;

    ReportOutPutFormat(int code) {
        this.format = code;
    }

    public int getCode() {
        return format;
    }

    public static ReportOutPutFormat fromCode(int code) {
        for (ReportOutPutFormat outPutFormat : ReportOutPutFormat.values()) {
            if (outPutFormat.getCode() == code) {
                return outPutFormat;
            }
        }
        throw new IllegalArgumentException("Invalid frequency code: " + code);
    }
}
