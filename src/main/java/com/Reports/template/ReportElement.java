package com.Reports.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportElement {
    Metrics metrics;
    TempplateComponentType component;
    String classPath;
    String dataSourceApi;
}
