package com.Reports.ReportGeneration;

import com.Reports.ReportGeneration.lib.DynamicReports.DynamicReports;
import com.Reports.engine.ReportInformation;
import net.sf.dynamicreports.report.exception.DRException;

public class ReportGemerator {

    public static int BuildReport(ReportInformation information) {
        DynamicReports dynamicReports = new DynamicReports(information);
        return 0;
    }
}
