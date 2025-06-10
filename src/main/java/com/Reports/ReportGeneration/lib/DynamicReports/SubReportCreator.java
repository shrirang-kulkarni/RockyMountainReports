package com.Reports.ReportGeneration.lib.DynamicReports;

import com.Reports.engine.ComponentInfo;
import com.Reports.engine.Components.MultiLineChartComponentData;
import com.Reports.engine.Components.PieChartComponentData;
import com.Reports.engine.Components.TableComponentData;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.chart.*;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.PenBuilder;
import net.sf.dynamicreports.report.builder.style.SimpleStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;


import java.awt.Color;
import java.util.*;


import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class SubReportCreator {

   private FontBuilder boldFont = stl.fontArialBold().setFontSize(12);

    public JasperReportBuilder createPieChartSubreport(ComponentInfo componentInfo) {



        DrDataSourceAPIs apIs = new DrDataSourceAPIs();

        PieChartComponentData pieChartComponentData = (PieChartComponentData)componentInfo.getComponentData();

        DRDataSource dataSource = apIs.getPieChartDataSource(pieChartComponentData);

        TextColumnBuilder<String> itemColumn = col.column( pieChartComponentData.getKeyFieldName(), "key", type.stringType())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<Integer> quantityColumn = col.column(pieChartComponentData.getValueFieldName(), "value", type.integerType())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);


        PieChartBuilder chartBuilders = cht.pieChart()
                .setTitle("Application Usage Chart")
                .setTitleFont(boldFont)
                .setKey(itemColumn)
                .setLabelFormat("{1}")
                .series(cht.serie(quantityColumn))
                .setDataSource(apIs.getPieChartDataSource(pieChartComponentData))
                .setWidth(300)
                .setHeight(300);


        // Blue border
        PenBuilder border = stl.penThin().setLineColor(Color.BLUE);

        StyleBuilder tableHeaderStyle = stl.style()
                .bold()
                .setBorder(border)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setPadding(5)
                .setBackgroundColor(new Color(230, 240, 255)) // light blue background
                .setFontSize(11);

        StyleBuilder tableCellStyle = stl.style()
                .setBorder(border)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setPadding(5)
                .setFontSize(10);

        // Alternate row (zebra effect)
        SimpleStyleBuilder oddRowStyle = stl.simpleStyle()
                .setBackgroundColor(new Color(245, 245, 245));


        SimpleStyleBuilder evenRowStyle = stl.simpleStyle()
                .setBackgroundColor(new Color(255, 255, 255));

        return report()
                .title(cmp.text("Report Sample for Pie Chart").setStyle(stl.style().bold()),cmp.verticalGap(5))
                .columns(itemColumn.setStyle(tableCellStyle).setTitleStyle(tableHeaderStyle),
                        quantityColumn.setStyle(tableCellStyle).setTitleStyle(tableHeaderStyle))
                .summary(
                        cmp.verticalGap(20),
                        chartBuilders
                )
                .setDataSource(dataSource)
                .setPageMargin(margin(40))
                .setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
    }

    public JasperReportBuilder createMultiLineChartSubreport(ComponentInfo componentInfo) {

        DrDataSourceAPIs apIs = new DrDataSourceAPIs();
        // Define a border style
        PenBuilder border = stl.penThin().setLineColor(Color.BLUE);

        // Header style
        StyleBuilder tableHeaderStyle = stl.style()
                .bold()
                .setBorder(border)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setPadding(5)
                .setBackgroundColor(new Color(230, 240, 255)) // Light blue background
                .setFontSize(11);

        // Cell style
        StyleBuilder tableCellStyle = stl.style()
                .setBorder(border)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setPadding(5)
                .setFontSize(10);

        // Alternate row style (zebra effect)
        SimpleStyleBuilder oddRowStyle = stl.simpleStyle()
                .setBackgroundColor(new Color(245, 245, 245));


        List<String> seriesNames = List.of("Product A", "Product B", "Product C", "Product D");

        List<TextColumnBuilder<?>> dynamicColumns = new ArrayList<>();
        dynamicColumns.add(Columns.column("Month", "month", DataTypes.stringType()));
        for (String series : seriesNames) {
            dynamicColumns.add(Columns.column(series, series, DataTypes.doubleType()));
        }

        List<CategoryChartSerieBuilder> chartSeries = new ArrayList<>();
        for (String seriesName : seriesNames) {
            CategoryChartSerieBuilder serie = Charts.serie(seriesName, Double.class)
                    .setLabel(seriesName);
            chartSeries.add(serie);
        }

        ComponentBuilder<?, ?> chart = cht.lineChart()
                .setCategory("month", String.class)
                .series(chartSeries.toArray(new CategoryChartSerieBuilder[0])).setShowShapes(false);

        MultiLineChartComponentData multiLineChartComponentData = (MultiLineChartComponentData) componentInfo.getComponentData();

        DRDataSource dataSource = apIs.getMultiLineChartDataSource(multiLineChartComponentData);

        // Apply styles to dynamic columns
        for (TextColumnBuilder<?> col : dynamicColumns) {
            col.setStyle(tableCellStyle).setTitleStyle(tableHeaderStyle);
        }

        // Build and return the JasperReportBuilder
        return report()
                .title(cmp.text("Dynamic Multi-Line Chart Report").setStyle(stl.style().bold()), cmp.verticalGap(5))
                .columns(dynamicColumns.toArray(new TextColumnBuilder[0]))
                .setDetailOddRowStyle(oddRowStyle)
                .summary(cmp.verticalGap(20), chart)
                .setDataSource(dataSource)
                .setPageMargin(margin(40))
                .setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
    }
/*
    public JasperReportBuilder createTableSubreport(ComponentInfo componentInfo) {
        DrDataSourceAPIs apIs = new DrDataSourceAPIs();
        TableComponentData tableComponentData = (TableComponentData) componentInfo.getComponentData();

        DRDataSource dataSource = apIs.getTableDataSource(tableComponentData);

        // Create columns dynamically based on metadata
        List<TextColumnBuilder<?>> columns = new ArrayList<>();
        List<String> fieldNames = Arrays.asList("name", "department", "salary"); // tableComponentData.getFieldNames();
        List<String> columnTitles = Arrays.asList("Name", "Department", "Salary"); //tableComponentData.getColumnTitles();

       for (int i = 0; i < fieldNames.size(); i++) {
            TextColumnBuilder<String> column = col.column(columnTitles.get(i), fieldNames.get(i), type.stringType())
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            columns.add(column);
        }

        // Styles
        PenBuilder border = stl.penThin().setLineColor(Color.BLUE);

        StyleBuilder tableHeaderStyle = stl.style()
                .bold()
                .setBorder(border)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setPadding(5)
                .setBackgroundColor(new Color(230, 240, 255)) // light blue
                .setFontSize(11);

        StyleBuilder tableCellStyle = stl.style()
                .setBorder(border)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setPadding(5)
                .setFontSize(10);

        // Apply styles to each column
        for (TextColumnBuilder<?> column : columns) {
            column.setStyle(tableCellStyle).setTitleStyle(tableHeaderStyle);
        }

        return report()
                .title(
                        cmp.text("Tabular Report").setStyle(stl.style().bold()),
                        cmp.verticalGap(5)
                )
                .columns(columns.toArray(new TextColumnBuilder[0]))
                .setDataSource(dataSource)
                .setPageMargin(margin(40))
                .setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
    }

 */

    public JasperReportBuilder createTableSubreport(ComponentInfo componentInfo) {
        DrDataSourceAPIs apIs = new DrDataSourceAPIs();
        TableComponentData tableComponentData = (TableComponentData) componentInfo.getComponentData();

        DRDataSource dataSource = apIs.getTableDataSource(tableComponentData);


        // Styles
        PenBuilder border = stl.penThin().setLineColor(Color.BLUE);

        StyleBuilder tableHeaderStyle = stl.style()
                .bold()
                .setBorder(border)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setPadding(5)
                .setBackgroundColor(new Color(230, 240, 255)) // light blue
                .setFontSize(11);

        StyleBuilder tableCellStyle = stl.style()
                .setBorder(border)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setPadding(5)
                .setFontSize(10);

        return report()
                .title(
                        cmp.text("Tabular Report").setStyle(stl.style().bold()),
                        cmp.verticalGap(5)
                )
                .columns( // dynamically add columns
                        tableComponentData.getColumns().stream()
                                .map(meta -> {
                                    TextColumnBuilder<?> col;
                                    if (meta.getType() == Integer.class) col = Columns.column(meta.getTitle(), meta.getField(), DataTypes.integerType());
                                    else if (meta.getType() == String.class) col = Columns.column(meta.getTitle(), meta.getField(), DataTypes.stringType());
                                    else if (meta.getType() == Double.class) col = Columns.column(meta.getTitle(), meta.getField(), DataTypes.doubleType());
                                    else if (meta.getType() == Boolean.class) col = Columns.column(meta.getTitle(), meta.getField(), DataTypes.booleanType());
                                    else if (meta.getType() == Date.class) col = Columns.column(meta.getTitle(), meta.getField(), DataTypes.dateType());
                                    else col = Columns.column(meta.getTitle(), meta.getField(), DataTypes.stringType());

                                    // Apply styles
                                    return col.setStyle(tableCellStyle).setTitleStyle(tableHeaderStyle);
                                })
                                .toArray(TextColumnBuilder[]::new)
                )
                .setDataSource(dataSource)
                .setPageMargin(margin(40))
                .setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
    }

}