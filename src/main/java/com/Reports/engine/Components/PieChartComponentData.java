package com.Reports.engine.Components;

import com.Reports.engine.ComponentData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PieChartComponentData extends ComponentData {
    private String title;
    private String keyFieldName;
    private String valueFieldName;
    List<KeyValueData> keyValueDataList = new ArrayList<>();
}
