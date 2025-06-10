package com.Reports.ReportGeneration.lib.DynamicReports;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import com.Reports.engine.ComponentInfo;
import com.Reports.engine.ReportComponentType;
import com.Reports.engine.ReportInformation;
import com.Reports.template.ReportOutPutFormat;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import static net.sf.dynamicreports.report.builder.DynamicReports.export;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DynamicReports {
    public DynamicReports(ReportInformation reportInformation) {
        try {
            build(reportInformation);
        } catch (DRException e) {
            throw new RuntimeException(e);
        }
    }
    private void build(ReportInformation reportInformation) throws DRException {

        FontBuilder boldFont = stl.fontArialBold().setFontSize(12);

        List<ComponentBuilder<?, ?>> componentBuilderList = new ArrayList<>();

        List<ComponentInfo> componentInfoList = reportInformation.getComponentInfos();

        List<ComponentBuilder<?, ?>> subreports = new ArrayList<>();

        DrDataSourceAPIs apIs = new DrDataSourceAPIs();

        SubReportCreator subReportCreator = new SubReportCreator();

        for (ComponentInfo componentInfo : componentInfoList) {
            if(componentInfo.getComponentType() == ReportComponentType.REPORT_COMP_TYPE_PIE_CHART){

                JasperReportBuilder chartReport = subReportCreator.createPieChartSubreport(componentInfo);

                // Wrap each subreport with a forced page break
                subreports.add(cmp.subreport(chartReport));
                subreports.add(cmp.pageBreak()); // force new page
            }
            else if (componentInfo.getComponentType() == ReportComponentType.REPORT_COMP_TYPE_TABLE) {
                JasperReportBuilder subreport = subReportCreator.createTableSubreport(componentInfo);

                //JasperReportBuilder subreport = subReportCreator.CreateXYLineChartSubReport();

                subreports.add(cmp.subreport(subreport));
                subreports.add(cmp.pageBreak());
            }else if(componentInfo.getComponentType() == ReportComponentType.REPORT_COMP_TYPE_BAR_CHART){

            }else if(componentInfo.getComponentType() == ReportComponentType.REPORT_COMP_TYPE_LINE_GRAPH){



                JasperReportBuilder subreport = subReportCreator.createMultiLineChartSubreport(componentInfo);

                //JasperReportBuilder subreport = subReportCreator.CreateXYLineChartSubReport();

                subreports.add(cmp.subreport(subreport));
                subreports.add(cmp.pageBreak());


            }
        }


        JasperReportBuilder jasperReportBuilder = net.sf.dynamicreports.report.builder.DynamicReports.report()
                .setTemplate(Templates.reportTemplate)
                .title(Templates.createTitleComponent(reportInformation.getTitle()).setStyle(stl.style().bold().setFontSize(16)))
                .summary(
                        cmp.verticalList(subreports.toArray(new ComponentBuilder[0]))
                )
                .pageFooter(Templates.footerComponent)
                .show();


        if(reportInformation.getOutPutFormat() == ReportOutPutFormat.PDF){
            try {
                jasperReportBuilder.toPdf(new FileOutputStream("output1.pdf"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else if(reportInformation.getOutPutFormat() == ReportOutPutFormat.HTML){
            JasperHtmlExporterBuilder htmlExporter = export.htmlExporter("output1.html")
                    .setImagesDirName("images")
                    .setOutputImagesToDir(true);
            jasperReportBuilder.toHtml(htmlExporter);
        }
    }

    // Create the chart data (example with 4 products and monthly data)
    private static JRDataSource createChartData( ) {
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
}
