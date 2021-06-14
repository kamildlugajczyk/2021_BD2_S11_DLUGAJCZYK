package pl.polsl.tab.fleetmanagement.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingRepository;
import pl.polsl.tab.fleetmanagement.person.PersonDTO;
import pl.polsl.tab.fleetmanagement.person.PersonService;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleDTO;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class KeepingReportService {

    private static final String logoImgPath = "src/main/resources/politechnika.jpg";
    private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font employeeFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font dateFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private static BaseFont helvetica;
    private static final String localDateFormat = "d MMMM yyyy HH:mm:ss";
    private Format formatter = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");

    private final PersonService personService;
    private final VehicleService vehicleService;
    private final KeepingRepository keepingRepository;


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

    public KeepingReportService(PersonService personService, VehicleService vehicleService, KeepingRepository keepingRepository) {
        this.personService = personService;
        this.vehicleService = vehicleService;
        this.keepingRepository = keepingRepository;
    }

    public void generateReport(HttpServletResponse response, Set<Long> vehicleIds) {
        response.setContentType("application/pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            addMetaData(document);
            addLogo(document);
            addTitle(document);
            addTable(document, vehicleIds);
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
        preface.add(new Paragraph("History vehicles keepers report", titleFont));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Report generated on: " + localDateString, dateFont));
        addEmptyLine(preface, 3);
        document.add(preface);
    }

    private void addTable(Document document, Set<Long> vehiclesId) throws DocumentException {
        for (Long vehicleId: vehiclesId) {
            VehicleDTO vehicle = vehicleService.getVehicle(vehicleId);

            String paragraphTitle = "Vehicle (ID): "
                    + vehicle.getBrandmodel().getBrand()
                    + " "
                    + vehicle.getBrandmodel().getModel()
                    + " ("
                    + vehicle.getId()
                    + ")";

            Paragraph paragraph = new Paragraph(paragraphTitle, employeeFont);
            addEmptyLine(paragraph, 1);
            document.add(paragraph);
            List<String> columnNames = Arrays.asList("Start date", "End date", "Surname", "Name", "Person ID");

            PdfPTable table = new PdfPTable(columnNames.size());

            for (String columnName : columnNames) {
                PdfPCell cell = new PdfPCell(new Phrase(columnName));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            table.setHeaderRows(1);
            getInfo(table, vehicleId);
            document.add(table);

//            Paragraph paragraph2 = new Paragraph("", employeeFont);
//            addEmptyLine(paragraph2, 1);
//            document.add(paragraph2);
//
//            List<String> columnNames2 = Arrays.asList("Surname", "Name", "Person ID",  "Start date", "End date");
//
//            PdfPTable table2 = new PdfPTable(columnNames2.size());
//
//            for (String columnName : columnNames2) {
//                PdfPCell cell = new PdfPCell(new Phrase(columnName));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                table2.addCell(cell);
//            }
//
//            table2.setHeaderRows(1);
//            getInfo2(table2, vehicleId);
//            document.add(table2);
        }
    }

    private void getInfo(PdfPTable table, Long vehicleId) {

        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        Iterable<KeepingEntity> iterable = this.keepingRepository.findAllByVehiclesId(vehicleId);
        Stream<KeepingEntity> streamKeepers = StreamSupport.stream(
                iterable.spliterator(), false)
                .sorted((o1, o2) -> {
                    if (o1.getStartdate() == null || o2.getStartdate() == null)
                        return 0;

                    if (o1.getStartdate().equals(o2.getStartdate())) {
                        if (o1.getEnddate() == null || o2.getEnddate() == null)
                            return 0;
                        return o2.getEnddate().compareTo(o1.getEnddate());
                    }

                    return o2.getStartdate().compareTo(o1.getStartdate());
                });

        for(KeepingEntity keeper: streamKeepers.collect(Collectors.toList())) {
            PersonDTO person = this.personService.getPerson(keeper.getPeopleId());

            table.addCell(formatter.format(keeper.getStartdate()));
            table.addCell(keeper.getEnddate() != null ? formatter.format(keeper.getEnddate()) : "-");
            table.addCell(person.getLastname());
            table.addCell(person.getFirstname());
            table.addCell(Long.toString(keeper.getPeopleId()));

        }
    }

//    private void getInfo2(PdfPTable table, Long vehicleId) {
//
//        table.setWidthPercentage(100);
//        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//        Iterable<KeepingEntity> iterable = this.keepingRepository.findAllByVehiclesId(vehicleId);
//        Map<Long, Set<KeepingEntity>> map = new HashMap<>();
//
//        for (KeepingEntity iterator: iterable) {
//            if (!map.containsKey(iterator.getPeopleId())) {
//                map.put(iterator.getPeopleId(), new HashSet<>());
//            }
//
//            map.get(iterator.getPeopleId()).add(iterator);
//        }
//
//        List<PersonDTO> nonSortedKeys = new ArrayList<>();
//        for (Map.Entry<Long, Set<KeepingEntity>> item: map.entrySet()) {
//            PersonDTO person = this.personService.getPerson(item.getKey());
//            nonSortedKeys.add(person);
//        }
//
//        List<PersonDTO> sortedKeysBySurname = nonSortedKeys
//                .stream()
//                .sorted((o1, o2) -> {
//                    if(o1 == null || o2 == null) {
//                        return 0;
//                    }
//
//                    if(o1.getLastname().equals(o2.getLastname())) {
//                        return o1.getFirstname().compareTo(o2.getFirstname());
//                    }
//
//                    return o1.getLastname().compareTo(o2.getLastname());
//                })
//                .collect(Collectors.toList());
//
//        for(PersonDTO personDTO: sortedKeysBySurname) {
//            int numOfDates = map.get(personDTO.getId()).size();
//
//            PdfPCell cellSurname = new PdfPCell(new Phrase(personDTO.getLastname()));
//            cellSurname.setRowspan(numOfDates);
//            PdfPCell cellName = new PdfPCell(new Phrase(personDTO.getFirstname()));
//            cellName.setRowspan(numOfDates);
//            PdfPCell cellId = new PdfPCell(new Phrase(Long.toString(personDTO.getId())));
//            cellId.setRowspan(numOfDates);
//
//            table.addCell(cellSurname);
//            table.addCell(cellName);
//            table.addCell(cellId);
//
//            for (KeepingEntity value : map.get(personDTO.getId())) {
//
//                table.addCell(value.getStartdate().toString());
//                table.addCell(value.getEnddate() != null ? value.getEnddate().toString() : "-");
//            }
//        }
//    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}