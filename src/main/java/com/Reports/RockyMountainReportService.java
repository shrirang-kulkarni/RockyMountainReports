package com.Reports;

import com.Reports.engine.ReportInformation;
import com.Reports.engine.ReportingEngine;
import com.Reports.helpers.FileOperations;
import com.Reports.template.TempplateComponentType;
import com.Reports.template.Metrics;
import com.Reports.template.ReportElement;
import com.Reports.template.ReportTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class RockyMountainReportService {

    public static void main(String[] args) throws IOException {

        System.out.println("Enter the option");
        System.out.println("Create template  --- 1");
        System.out.println("Generate Report  --- 2");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        do {
            switch (option) {
                case 1: CreateTemplate( ); break;
                case 2: GenerateReport(); break;
            }
        }while ( (option == 1 ) && option == 2);
    }

    private static int GenerateReport( )
    {

        String filePath = "/Users/shkulkarni/Project/Rocky Mountain/Code/RockyMountainReports/report_template.json";

        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        try {

            File file = new FileOperations( ).getFileHandler("report_template.json");

            List<ReportTemplate> reportList = null;

            if(file.length() > 0) {
                reportList = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, ReportTemplate.class));


                System.out.println("Select the template");
                if (reportList != null && reportList.size() > 0) {
                    System.out.printf("|%-45s|%25s|%-65s|%15s|%n","ID","Name","Description","Created By");
                    System.out.println();
                    for (ReportTemplate reportTemplate : reportList) {
                        System.out.printf("|%-45s|%25s|%-65s|%15s|%n",reportTemplate.getId()
                                ,reportTemplate.getName()
                                ,reportTemplate.getDescription()
                                ,reportTemplate.getCreatedBy());
                    }

                }
            }

            Scanner scanner = new Scanner(System.in);
            String UUID = scanner.nextLine().trim();

            Map<String, ReportTemplate> map = reportList.stream()
                    .collect(Collectors.toMap(
                            ReportTemplate::getId,
                            ReportTemplate -> ReportTemplate
                    ));

            ReportingEngine reportEngine = new ReportingEngine();
            ReportTemplate template = map.get(UUID);
            ReportInformation reportInformation =  reportEngine.TemplateParser(template);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    private static  int CreateTemplate( )
    {
        try
        {

            ObjectMapper mapper = new ObjectMapper( );

            List<ReportTemplate> reportList = null;

            File file = new FileOperations( ).getFileHandler("report_template.json");

            if(file.length() > 0){
                reportList = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, ReportTemplate.class));
            }else {
                reportList = new ArrayList<>();
            }

            ReportElement reportElement = new ReportElement();
            ReportTemplate report = new ReportTemplate();


            System.out.println("Enter Bellow Information");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Name");
            String name = scanner.nextLine().trim();
            report.setName(name);

            System.out.print("Title");
            String title = scanner.nextLine().trim();
            report.setTitle(title);

            System.out.print("Description");
            String description = scanner.nextLine().trim();
            report.setDescription(description);

            System.out.print("Start Time in epoch");
            Long startTime = scanner.nextLong();scanner.nextLine();
            report.setStartTime(startTime);

            System.out.print("End Time in epoch");
            Long endTime = scanner.nextLong(); scanner.nextLine();
            report.setEndTime(endTime);

            System.out.print("Create By");
            String createBy = scanner.nextLine().trim();
            report.setCreatedBy(createBy);

            System.out.print("Enter number of metrics");
            int metricsCount = scanner.nextInt();
            scanner.nextLine();

            List<ReportElement> reportElementList = new ArrayList<ReportElement>();
            for (int i = 0; i < metricsCount; i++)
            {
                ReportElement element = new ReportElement();
                System.out.println("Select Metric");
                System.out.println("1 --- MAX_CONCURRENT_CLIENTS_OVER_TIME(\"Max concurrent clients over time\")");
                System.out.println("2 --- UNIQUE_CLIENTS_OVER_TIME_BY_SSID(\"Unique clients over time by SSID\")");
                System.out.println("3 --- UNIQUE_WIFI_CLIENTS_OVER_TIME(\"Unique Wi-Fi clients over time\")");
                System.out.println("4 --- CLIENT_QUALITY_SCORE_OVER_TIME(\"Client quality score over time\")");
                System.out.println("5 --- CLIENT_SESSIONS_OVER_TIME(\"Client sessions over time\")");
                System.out.println("6 --- CLIENT_AIRTIME_USAGE_OVER_TIME(\"Client Airtime usage over time\")");
                System.out.println("7 --- WIRELESS_CLIENTS_BY_OS(\"Wireless clients by OS\")");
                System.out.println("8 --- UNIQUE_CLIENTS_BY_OS(\"Unique clients by OS\")");
                System.out.println("9 --- DEVICES_BY_CLIENTS_OVER_TIME(\"Devices by clients over time\")");
                System.out.println("10 -- SWITCH_SUMMARY(\"Switch Summary\")");
                int metricsName = Integer.parseInt(scanner.nextLine().trim());
                element.setMetrics(Metrics.fromInt(metricsName));

                System.out.println("Select Component");
                System.out.println("1 ---   TABLE");
                System.out.println("2 ---   BAR_CHART");
                System.out.println("3 ---   PIE_CHART");
                System.out.println("4 ---   LINE_GRAPH");
                element.setComponent(TempplateComponentType.fromInt(Integer.parseInt(scanner.nextLine().trim())));

                System.out.println("class Name");
                String classname = scanner.nextLine();
                element.setClassPath(classname);

                System.out.println("Data Source API Name");
                String api = scanner.nextLine();
                element.setDataSourceApi(api);

                reportElementList.add(element);
            }
            report.setReportElements(reportElementList);

            report.setId(UUID.randomUUID().toString());
            reportList.add(report);

            // Serialize and write to file
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, reportList);
            System.out.println("JSON written to: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}