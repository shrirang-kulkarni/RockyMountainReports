package com.Reports.ReportGeneration.lib.DynamicReports;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import com.Reports.engine.Components.KeyValueData;
import com.Reports.engine.Components.PieChartComponentData;
import com.Reports.engine.ReportInformation;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class DynamicReports {
    public DynamicReports(ReportInformation reportInformation) {
        build(reportInformation);
    }
    private void build(ReportInformation reportInformation) {

        PieChartComponentData pieChartComponentData = (PieChartComponentData)reportInformation.getComponentInfos().get(0).getComponentData();

        FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
        TextColumnBuilder<String> itemColumn = col.column( "KeyField", "key", type.stringType())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        TextColumnBuilder<Integer> quantityColumn = col.column(pieChartComponentData.getValueFieldName(), "value", type.integerType())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        try {
            JasperReportBuilder jasperReportBuilder = net.sf.dynamicreports.report.builder.DynamicReports.report()
                    .setTemplate(Templates.reportTemplate)
                    .columns(itemColumn, quantityColumn)
                    .title(Templates.createTitleComponent("PieChart"))
                    .summary(
                            cht.pieChart()
                                    .setTitle(reportInformation.getTitle())
                                    .setTitleFont(boldFont)
                                    .setKey(itemColumn)
                                    .setLabelFormat("{1}")
                                    .series(
                                            cht.serie(quantityColumn)))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(pieChartComponentData.getKeyValueDataList())
                    .toPdf(new FileOutputStream("report_output.pdf"));

        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static JRDataSource createDataSource(List<KeyValueData> dataList) {
        return new JRBeanCollectionDataSource(dataList);
    }
}
