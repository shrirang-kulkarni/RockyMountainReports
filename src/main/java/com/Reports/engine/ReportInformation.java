package com.Reports.engine;

import com.Reports.template.ReportElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportInformation {
    private String name;
    private String title;
    private String description;
    private Long startTime;
    private Long endTime;
    private String createdBy;
    private List<ComponentInfo> componentInfos = new ArrayList<>();
}
