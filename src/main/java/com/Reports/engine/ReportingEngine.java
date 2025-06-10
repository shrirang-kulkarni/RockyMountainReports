package com.Reports.engine;

import com.Reports.ApiService.PieChartDataSource;
import com.Reports.ApiService.TableChartDataSource;
import com.Reports.engine.Components.PieChartComponentData;
import com.Reports.ReportGeneration.ReportGemerator;
import com.Reports.engine.Components.TableComponentData;
import com.Reports.template.TempplateComponentType;
import com.Reports.template.ReportElement;
import com.Reports.template.ReportTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.Reports.engine.ReportComponentType.*;
import static com.Reports.template.TempplateComponentType.*;

public class ReportingEngine {

    private static final Map<TempplateComponentType,ReportComponentType> MetricsToComponentTypeMap = new HashMap< >();

    static {
        MetricsToComponentTypeMap.put(TEMP_COMP_TYPE_TABLE, REPORT_COMP_TYPE_TABLE);
        MetricsToComponentTypeMap.put(TEMP_COMP_TYPE_BAR_CHART,ReportComponentType.REPORT_COMP_TYPE_BAR_CHART);
        MetricsToComponentTypeMap.put(TEMP_COMP_TYPE_PIE_CHART,ReportComponentType.REPORT_COMP_TYPE_PIE_CHART);
        MetricsToComponentTypeMap.put(TEMP_COMP_TYPE_LINE_GRAPH,ReportComponentType.REPORT_COMP_TYPE_LINE_GRAPH);
    }

    public ReportInformation  TemplateParser(ReportTemplate template) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {

        ReportInformation reportInformation = new ReportInformation();

        reportInformation.setTitle(template.getTitle());
        reportInformation.setDescription(template.getDescription());
        reportInformation.setName(template.getName());
        reportInformation.setOutPutFormat(template.getOutPutFormat());

        if (template.getReportElements() != null && template.getReportElements().size() > 0) {
            List<ComponentInfo> componentInfos = new ArrayList<>();
            List<ReportElement> reportElements = template.getReportElements();

            for (ReportElement element : reportElements) {
                ComponentInfo componentInfo = new ComponentInfo();
                componentInfo.setComponentType(MetricsToComponentTypeMap.get(element.getComponent()));


                String classpath = element.getClassPath();
                if (classpath != null && classpath.length() > 0) {
                    // Load the class
                    Class<?> clazz = Class.forName(classpath);

                    if (clazz != null) {

                        ReportComponentType type = componentInfo.getComponentType();

                       if(type == REPORT_COMP_TYPE_TABLE){
                           if(TableChartDataSource.class.isAssignableFrom(clazz)){
                               // Create an instance
                               Object apiService = clazz.getDeclaredConstructor().newInstance();

                               String methodname = element.getDataSourceApi();

                               // Get the method with parameter type
                               Method method = clazz.getMethod(methodname, null);

                               // Invoke the method
                               TableComponentData data = (TableComponentData) method.invoke(apiService, null);

                               componentInfo.setComponentData(data);
                               componentInfos.add(componentInfo);
                           }
                           //componentInfos.add(componentInfo);
                       }
                       else if(type == REPORT_COMP_TYPE_BAR_CHART ){

                       }
                       else if (type == REPORT_COMP_TYPE_LINE_GRAPH){
                         //  if(LineGraphComponentData.class.isAssignableFrom(clazz)){
                               // Create an instance
                            //   Object apiService = clazz.getDeclaredConstructor().newInstance();

                              // String methodname = element.getDataSourceApi();

                               // Get the method with parameter type
                             //  Method method = clazz.getMethod(methodname, null);

                               // Invoke the method
                             //  LineGraphComponentData data = (LineGraphComponentData) method.invoke(apiService, null);

                               //componentInfo.setComponentData(data);
                               componentInfos.add(componentInfo);
                          // }
                       }
                       else if(type == REPORT_COMP_TYPE_PIE_CHART) {
                           if(PieChartDataSource.class.isAssignableFrom(clazz)){
                               // Create an instance
                               Object apiService = clazz.getDeclaredConstructor().newInstance();

                               String methodname = element.getDataSourceApi();

                               // Get the method with parameter type
                               Method method = clazz.getMethod(methodname, null);

                               // Invoke the method
                               PieChartComponentData data = (PieChartComponentData)method.invoke(apiService, null);

                               componentInfo.setComponentData(data);
                               componentInfos.add(componentInfo);
                           }
                       }
                    }
                }
            }
            reportInformation.setComponentInfos(componentInfos);
        }
        ReportGemerator.BuildReport(reportInformation);
        return reportInformation;
    }

    private int FetchDataFromSource( ){

        return 0;
    }

    private  int GenerateReport( ){

        return 0;
    }
}
