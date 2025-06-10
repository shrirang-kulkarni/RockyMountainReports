package com.Reports.ApiService.Impl;

import com.Reports.ApiService.TableChartDataSource;
import com.Reports.engine.Components.ColumnMeta;
import com.Reports.engine.Components.TableComponentData;

import java.util.*;

public class DummyTableDataSource  implements TableChartDataSource {
    @Override
    public TableComponentData getTableChartData() {

        TableComponentData tableComponentData = new TableComponentData();

        List<ColumnMeta> metaList = List.of(
                new ColumnMeta("ID", "id", Integer.class),
                new ColumnMeta("Name", "name", String.class),
                new ColumnMeta("Price", "price", Double.class),
                new ColumnMeta("Discount", "discount", Double.class),
                new ColumnMeta("Available", "available", Boolean.class),
                new ColumnMeta("Created At", "createdAt", Date.class),
                new ColumnMeta("Modified At", "modifiedAt", Date.class)
        );

        List<Map<String, Object>> data = new ArrayList<>();
        int idx = 0;
        Object[][] rawData = {
                {idx++, "Widget", 19.99, 10.00,true, new Date(),new Date()}, {idx++, "Gadget", 29.99, 10.00,false, new Date(),new Date()}, {idx++, "Gadget", 29.99,10.00, false, new Date(),new Date()},
                /*{idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Widget", 19.99, true, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()},
                {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Widget", 19.99, true, new Date(),new Date()},
                {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()},
                {idx++, "Widget", 19.99, true, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()},
                {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Widget", 19.99, true, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()},
                {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()},
                {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()},
                {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(),new Date()},
                {idx++, "Gadget", 29.99, false, new Date(),new Date()}, {idx++, "Gadget", 29.99, false, new Date(), new Date()}*/
        };

        String[] keys = {"id", "name", "price", "discount", "available", "createdAt","modifiedAt"};

        for (Object[] rowValues : rawData) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 0; i < keys.length; i++) {
                row.put(keys[i], rowValues[i]);
            }
            data.add(row);
        }

        tableComponentData.setColumns(metaList);
        tableComponentData.setRows(data);

        return tableComponentData;
    }
}
