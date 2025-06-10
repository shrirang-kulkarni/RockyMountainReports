package com.Reports.ApiService.Impl;

import com.Reports.ApiService.PieChartDataSource;
import com.Reports.engine.Components.KeyValueData;
import com.Reports.engine.Components.PieChartComponentData;

import java.util.ArrayList;
import java.util.List;

public class ApplicationUsageDataSource implements PieChartDataSource {
    public PieChartComponentData getPieChartData() {
        PieChartComponentData pieChartComponentData = new PieChartComponentData();
        pieChartComponentData.setTitle("Application Usage");
        pieChartComponentData.setKeyFieldName("Application Name");
        pieChartComponentData.setValueFieldName("Usage in %");
        List<KeyValueData> keyValueDataList = new ArrayList<>();
        keyValueDataList.add(new KeyValueData("App1", 10));
        keyValueDataList.add(new KeyValueData("App2", 10));
        keyValueDataList.add(new KeyValueData("App3", 5));
        keyValueDataList.add(new KeyValueData("App4", 30));
        keyValueDataList.add(new KeyValueData("App5", 10));
        keyValueDataList.add(new KeyValueData("App6", 1));

        pieChartComponentData.setKeyValueDataList(keyValueDataList);
        return pieChartComponentData;
    }
}
