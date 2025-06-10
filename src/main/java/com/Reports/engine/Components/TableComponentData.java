package com.Reports.engine.Components;

import com.Reports.engine.ComponentData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TableComponentData extends ComponentData {
   List<ColumnMeta> columns; //column information
   List<Map<String, Object>> rows; //row data
}
