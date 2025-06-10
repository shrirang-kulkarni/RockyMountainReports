package com.Reports.engine.Components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ColumnMeta {
    String title;
    String field;
    Class<?> type;

}
