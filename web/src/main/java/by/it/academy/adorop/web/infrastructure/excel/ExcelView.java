package by.it.academy.adorop.web.infrastructure.excel;

import by.it.academy.adorop.model.Course;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Sheet sheet = createSheetWithHeader(workbook);
        List<Course> courses = (List<Course>) request.getAttribute("courses");
        int rowNumber = 1;
        for (Course course : courses) {
            Row row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(course.getTitle());
            row.createCell(1).setCellValue(course.getDescription());
            row.createCell(2).setCellValue(course.getTeacher().getLastName());
            rowNumber++;
        }
    }

    private Sheet createSheetWithHeader(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Courses");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Title");
        header.createCell(1).setCellValue("Description");
        header.createCell(2).setCellValue("Teacher");
        return sheet;
    }
}
