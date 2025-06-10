package com.Reports.ReportGeneration.lib.DynamicReports;

import com.Reports.engine.Components.*;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DrDataSourceAPIs {

    public DRDataSource getPieChartDataSource(PieChartComponentData pieChartComponentData) {
        DRDataSource dataSource = new DRDataSource("key", "value");
        List<KeyValueData> keyValueDataList = pieChartComponentData.getKeyValueDataList();
        for (KeyValueData keyValueData : keyValueDataList) {
            dataSource.add(keyValueData.getKey(), keyValueData.getValue());
        }
        return dataSource;
    }

    public DRDataSource getMultiLineChartDataSource(MultiLineChartComponentData multiLineChartComponentData) {
        DRDataSource dataSource = new DRDataSource("month", "Product A", "Product B", "Product C", "Product D");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = sdf.parse("2025-01-15");
            Date d2 = sdf.parse("2025-02-10");
            Date d3 = sdf.parse("2025-03-05");
            Date d4 = sdf.parse("2025-04-20");
            Date d5 = sdf.parse("2025-05-27");
            // Example data for the months)
            dataSource.add("Jan",   120.0,    150.0,  90.0,   110.0 );
            dataSource.add("Feb",   200.0,    180.0,  150.0,  250.0 );
            dataSource.add("Mar",   90.0,     140.0,  110.0,  95.0  );
            dataSource.add("Apr",   200.0,    100.0,  150.0,  250.0 );
            dataSource.add("May",   100.0,    150.0,  50.0,   100.0 );
            return dataSource;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public DRDataSource getTableDataSource(TableComponentData tableComponentData) {
        /*DRDataSource dataSource = new DRDataSource("name", "department", "salary");

        dataSource.add("Alice", "IT", "1500");
        dataSource.add("Bob", "HR", "1300");
        dataSource.add("Charlie", "Finance", "1700");
        */

        // Extract field names in order
        String[] fields = tableComponentData.getColumns().stream().map(columnMeta -> columnMeta.getField()).toArray(String[]::new);

        DRDataSource dataSource = new DRDataSource(fields);

        List<Map<String,Object>> data = tableComponentData.getRows();
        // Add row values based on field order
        for (Map<String, Object> row : data) {
            Object[] values = tableComponentData.getColumns().stream()
                    .map(meta -> row.getOrDefault(meta.getField(), null))
                    .toArray();
            dataSource.add(values);
        }
        return dataSource;
    }
}
