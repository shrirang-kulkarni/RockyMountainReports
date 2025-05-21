package com.Reports.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentInfo {
    private ReportComponentType componentType;
    private ComponentData componentData;
}
