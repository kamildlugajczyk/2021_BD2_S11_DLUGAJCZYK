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
public class ReportService {

    private static final String logoImgPath = "src/main/resources/politechnika.jpg";
    private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font employeeFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font dateFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private static BaseFont helvetica;
    private static final String localDateFormat = "d MMMM yyyy HH:mm:ss";

    private final PersonService personService;
    private final VehicleRentingService vehicleRentingService;
    private final VehicleUnavailabilityService vehicleUnavailabilityService;
    private final VehicleService vehicleService;
    private final OperationCostService operationCostService;
    private final OperationTypeService operationTypeService;


    static {
        try {
            helvetica = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Font helvetica12 = new Font(helvetica, 12);

    public ReportService(PersonService personService, VehicleRentingService vehicleRentingService, VehicleUnavailabilityService vehicleUnavailabilityService, VehicleService vehicleService, OperationCostService operationCostService, OperationTypeService operationTypeService) {
        this.personService = personService;
        this.vehicleRentingService = vehicleRentingService;
        this.vehicleUnavailabilityService = vehicleUnavailabilityService;
        this.vehicleService = vehicleService;
        this.operationCostService = operationCostService;
        this.operationTypeService = operationTypeService;
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

    private static void addContent(Document document) throws DocumentException {
//        Anchor anchor = new Anchor("First Chapter", titleFont);
//        anchor.setName("First Chapter");
//
//        // Second parameter is the number of the chapter
//        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
//
//        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
//        Section subCatPart = catPart.addSection(subPara);
//        subCatPart.add(new Paragraph("Gram sety gorące jak Diplo cały kraj się jara moją ksywką\n" +
//                "Cały kraj jara się moją bibką kiedy za stołem miksuję ten hip hop\n" +
//                "Miksuję ten hip hop wszystko co zrobiłam to było dopiero intro\n" +
//                "Z dancehallem miksuję ten hip hop bomboclaat\n" +
//                "Skaczesz na parkiecie a zrobiło się już widno\n" +
//                "Kiedyś to codziennie miałam klub teraz mam tu pełny stół\n" +
//                "Z ziomalami dzielę się na pół każdy ruch to wspólny move\n" +
//                "Chcę dostać ordery królowej imprezy a potem królowej afterów\n" +
//                "Bo mi się należy (tak jest)\n" +
//                "Wie każdy kto ze mną coś przeżył\n" +
//                "Noce nie bywają spokojne będzie trzeba to pójdę na wojnę\n" +
//                "Na ulicy w szeleszczącej kurtce Moncler będę walczyć o zwrot moich wspomnień\n" +
//                "Bo chcecie nam zabrać ten czas i te radosne bankiety\n" +
//                "Oddajcie lokale i hajs nie cenzurujcie poety\n" +
//                "Czekam aż znowu będziemy popijać Bacardi w klubie ze szklanki\n" +
//                "Gdy na parkiecie nasze fanki a na głośniku Nicki i Cardi\n" +
//                "Nie mów nikomu łamiemy zasady bo w domu też bywa funky\n" +
//                "Kiedy wpadają koleżanki zaczynają się hulanki\n" +
//                "Czekam aż znowu będziemy popijać Bacardi w klubie ze szklanki\n" +
//                "Gdy na parkiecie nasze fanki a na głośniku Nicki i Cardi\n" +
//                "Nie mów nikomu łamiemy zasady bo w domu też bywa funky\n" +
//                "Kiedy wpadają koleżanki zaczynają się hulanki\n" +
//                "Wbijaj do mnie na house party z nami bawią się sąsiadki\n" +
//                "Najpierw kozackie wieczory potem w radiu Bolesne Poranki\n" +
//                "Mój typ melanżu to takie tańczone rąk pełna sala przez noc i przez dobę\n" +
//                "Buja się cały klub z nogi na nogę chcę już wieczory spędzać poza domem\n" +
//                "Zawsze stać za DJ ką tam gdzie z nieba sypie się bankroll\n" +
//                "Tam gdzie laski tańczą na pewno błyszczą sukienką\n" +
//                "Chodzą z torebką gdzie chłopakom chodzi tylko o jedno\n" +
//                "W moim mieście chodzi tylko o jedno\n" +
//                "Czekam aż znowu będziemy popijać Bacardi w klubie ze szklanki\n" +
//                "Gdy na parkiecie nasze fanki a na głośniku Nicki i Cardi\n" +
//                "Nie mów nikomu łamiemy zasady bo w domu też bywa funky\n" +
//                "Kiedy wpadają koleżanki zaczynają się hulanki\n" +
//                "Czekam aż znowu będziemy popijać Bacardi w klubie ze szklanki\n" +
//                "Gdy na parkiecie nasze fanki a na głośniku Nicki i Cardi\n" +
//                "W moim mieście chodzi tylko o jedno\n" +
//                "Czekam aż znowu będziemy popijać Bacardi w klubie ze szklanki\n" +
//                "Gdy na parkiecie nasze fanki a na głośniku Nicki i Cardi\n" +
//                "Nie mów nikomu łamiemy zasady bo w domu też bywa funky\n" +
//                "Kiedy wpadają koleżanki zaczynają się hulanki\n" +
//                "Czekam aż znowu będziemy popijać Bacardi w klubie ze szklanki\n" +
//                "Gdy na parkiecie nasze fanki a na głośniku Nicki i Cardi\n" +
//                "Nie mów nikomu łamiemy zasady bo w domu też bywa funky\n" +
//                "Kiedy wpadają koleżanki zaczynają się hulanki", helvetica12));
//
//        subPara = new Paragraph("Subcategory 2", subFont);
//        subCatPart = catPart.addSection(subPara);
//        subCatPart.add(new Paragraph("Paragraph 1"));
//        subCatPart.add(new Paragraph("Paragraph 2"));
//        subCatPart.add(new Paragraph("Paragraph 3"));
//
//        // add a list
//        createList(subCatPart);
//        Paragraph paragraph = new Paragraph();
//        addEmptyLine(paragraph, 5);
//        subCatPart.add(paragraph);
//
//        // add a table
//        createTable(subCatPart);
//
//        // now add all this to the document
//        document.add(catPart);
//
//        // Next section
//        anchor = new Anchor("Second Chapter", titleFont);
//        anchor.setName("Second Chapter");
//
//        // Second parameter is the number of the chapter
//        catPart = new Chapter(new Paragraph(anchor), 1);
//
//        subPara = new Paragraph("Subcategory", subFont);
//        subCatPart = catPart.addSection(subPara);
//        subCatPart.add(new Paragraph("This is a very important message"));
//
//        // now add all this to the document
//        document.add(catPart);


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
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
        }
        table.setHeaderRows(1);
        getInfo(table, personId);
        document.add(table);
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

            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(vehicleUnavailabilityDto.getVehiclesId().toString());
            table.addCell(vehicleName);
            table.addCell(vehicleUnavailabilityDto.getStartDate().toString());
            table.addCell(vehicleUnavailabilityDto.getEndDate().toString());

            int i = 0;
            for (String name : operationNames){
                if (i++ == operationNames.size() - 1)
                    table.addCell(name);
                else
                    table.addCell(name + ", ");
            }

            table.addCell(String.valueOf(mileage));
            table.addCell(String.valueOf(operationsCostValue) + " PLN");
            table.addCell(String.valueOf(fuelCostByOneKilometer * mileage) + " PLN");
        }

    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph(
                "------------------------End Of Employee Report------------------------", helvetica12);
        addEmptyLine(footer, 3);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }

    private static void addTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

//    private static void createList(Section subCatPart) {
//        List list = new List(true, false, 10);
//        list.add(new ListItem("First point"));
//        list.add(new ListItem("Second point"));
//        list.add(new ListItem("Third point"));
//        subCatPart.add(list);
//    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
