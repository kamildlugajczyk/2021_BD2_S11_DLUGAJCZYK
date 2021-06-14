package pl.polsl.tab.fleetmanagement.report;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.exploitation.OperationCostDto;
import pl.polsl.tab.fleetmanagement.exploitation.OperationCostService;
import pl.polsl.tab.fleetmanagement.exploitation.type.OperationTypeService;
import pl.polsl.tab.fleetmanagement.person.PersonDTO;
import pl.polsl.tab.fleetmanagement.person.PersonService;
import pl.polsl.tab.fleetmanagement.rentings.VehicleRentingService;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleService;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityDto;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class EmployeeReportService {

    private static final String logoImgPath = "src/main/resources/politechnika.jpg";
    private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font employeeFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font dateFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private static BaseFont helvetica;
    private static final String localDateFormat = "d MMMM yyyy HH:mm:ss";
    private double totalCosts = 0.0;

    private final PersonService personService;
    private final VehicleRentingService vehicleRentingService;
    private final VehicleUnavailabilityService vehicleUnavailabilityService;
    private final VehicleService vehicleService;
    private final OperationCostService operationCostService;


    static {
        try {
            helvetica = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final Font helvetica12 = new Font(helvetica, 12);

    public EmployeeReportService(PersonService personService, VehicleRentingService vehicleRentingService, VehicleUnavailabilityService vehicleUnavailabilityService, VehicleService vehicleService, OperationCostService operationCostService, OperationTypeService operationTypeService) {
        this.personService = personService;
        this.vehicleRentingService = vehicleRentingService;
        this.vehicleUnavailabilityService = vehicleUnavailabilityService;
        this.vehicleService = vehicleService;
        this.operationCostService = operationCostService;
    }

    public void generateReport(HttpServletResponse response) {
        response.setContentType("application/pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            addMetaData(document);
            addLogo(document);
            addTitle(document);
            addTable(document, 1L);
            addFooter(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Fleet Management report");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Grupa 2");
        document.addCreator("Grupa 2");
    }

    private static void addLogo(Document document) {
        try {
            Image img = Image.getInstance(logoImgPath);
            img.scalePercent(10.0F);
            img.setAlignment(Element.ALIGN_RIGHT);
            document.add(img);
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void addTitle(Document document) throws DocumentException {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                localDateFormat, new Locale("en")));
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Employee report", titleFont));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Report generated on: " + localDateString, dateFont));
        addEmptyLine(preface, 3);
        document.add(preface);
    }

    private void addTable(Document document, Long personId) throws DocumentException {
        PersonDTO person = personService.getPerson(personId);

        Paragraph paragraph = new Paragraph("Employee name: " + person.getFirstname() + " " +
                person.getLastname() + "\nEmployee ID: " + personId, employeeFont);
        addEmptyLine(paragraph, 1);
        document.add(paragraph);
        List<String> columnNames = Arrays.asList(
                "Vehicle ID", "Vehicle name", "Start date", "End date",
                "Operations", "Mileage", "Operations cost", "Fuel cost");

        PdfPTable table = new PdfPTable(columnNames.size());

        for (String columnName : columnNames) {
            PdfPCell cell = new PdfPCell(new Phrase(columnName));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }
        table.setHeaderRows(1);
        getInfo(table, personId);
        document.add(table);

        Paragraph costs = new Paragraph("Total costs generated by " + person.getFirstname() + " " +
                person.getLastname() + ": " + String.format("%.2f PLN", totalCosts));
        document.add(costs);
    }

    private void getInfo(PdfPTable table, Long personId) {


        List<VehicleUnavailabilityDto> vehicleUnavailabilityDtos =
                vehicleUnavailabilityService.getVehicleUnavailabilityByPersonId(personId);

        double fuelCostByOneKilometer = 0.45;

        for (VehicleUnavailabilityDto vehicleUnavailabilityDto : vehicleUnavailabilityDtos) {
            int mileage = vehicleRentingService.getVehicleRentingByVehicleUnavailability(
                    vehicleUnavailabilityDto.getId()).getEndmileage() -
                    vehicleRentingService.getVehicleRentingByVehicleUnavailability(
                            vehicleUnavailabilityDto.getId()).getStartmileage();
            String vehicleName = vehicleService.getVehicle(vehicleUnavailabilityDto
                    .getVehiclesId()).getBrandmodel().getBrand() +
                    " " + vehicleService.getVehicle(vehicleUnavailabilityDto
                    .getVehiclesId()).getBrandmodel().getModel();

            List<OperationCostDto> operationCostDtos = operationCostService
                    .getOperationCostsByVehicleRentingId(
                            vehicleRentingService.getVehicleRentingByVehicleUnavailability(vehicleUnavailabilityDto.getId())
                                    .getId().intValue());

            double operationsCostValue = operationCostDtos
                    .stream()
                    .mapToDouble(x -> x.getPrice().doubleValue())
                    .sum();

            List<String> operationNames = operationCostDtos
                    .stream()
                    .map(x -> x.getOperationType().getName())
                    .collect(Collectors.toList());

            StringBuilder operations = new StringBuilder();
            if (operationNames.isEmpty())
                operations.append(" - ");
            int i = 0;
            for (String name : operationNames) {
                if (i++ == operationNames.size() - 1)
                    operations.append(name);
                else
                    operations.append(name).append(", ");
            }

            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(vehicleUnavailabilityDto.getVehiclesId().toString());
            table.addCell(vehicleName);
            table.addCell(vehicleUnavailabilityDto.getStartDate().toString());
            table.addCell(vehicleUnavailabilityDto.getEndDate().toString());
            table.addCell(operations.toString());
            table.addCell(String.valueOf(mileage));
            table.addCell(String.format("%.2f PLN", operationsCostValue));
            table.addCell(String.format("%.2f PLN", fuelCostByOneKilometer * mileage));

            totalCosts += operationsCostValue;
            totalCosts += fuelCostByOneKilometer * mileage;
        }

    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph(
                "\n------------------------End Of Employee Report------------------------", helvetica12);
        addEmptyLine(footer, 3);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}