package com.Reports.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentInfo {
    private String componentTilte;
    private String componentdescription;
    private ReportComponentType componentType;
    private ComponentData componentData;
}
