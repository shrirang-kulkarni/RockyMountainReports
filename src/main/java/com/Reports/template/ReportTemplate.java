package com.Reports.template;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ReportTemplate {
    private String id;
    private String name;
    private String title;
    private String description;
    private Long startTime;
    private Long endTime;
    private String createdBy;
    private List<ReportElement> reportElements;
}
