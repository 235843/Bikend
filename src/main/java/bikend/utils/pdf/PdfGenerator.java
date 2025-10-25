package bikend.utils.pdf;

import bikend.domain.BikeEntity;
import bikend.domain.ReservationEntity;
import bikend.utils.DateHelper;
import bikend.utils.dtos.BikeDTO;
import bikend.utils.dtos.ReservationDTO;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {
    public static byte[] generateRegistrationPdf(String email, ReservationDTO reservation) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            long days = DateHelper.getDays(reservation.getReservationStart(), reservation.getReservationStop());
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("Potwierdzenie transakcji", titleFont));
            document.add(new Paragraph("\nUżytkownik: " + email, normalFont));
            document.add(new Paragraph("\nProdukty:", normalFont));
            for (BikeDTO bike : reservation.getBikeDTOList()) {
                StringBuilder paragraph = new StringBuilder("\n- ");
                paragraph.append(bike.getModel())
                        .append(" ").append(bike.getSeries())
                        .append(" / ").append(bike.getCount())
                        .append(" szt x ").append(bike.getPricePerDay())
                        .append(" pln x ").append(days) .append(" dni / ")
                        .append("Łącznie: ").append(days * bike.getCount() * bike.getPricePerDay()).append(" pln");
                document.add(new Paragraph(paragraph.toString(), normalFont));

            }
            document.add(new Paragraph("\nŁączna kwota: " + reservation.getCost(), normalFont));

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

