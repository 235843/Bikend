package bikend.utils.pdf;

import bikend.domain.BikeEntity;
import bikend.domain.ReservationEntity;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {
    public static byte[] generateRegistrationPdf(String email, ReservationEntity reservation) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("Potwierdzenie transakcji", titleFont));
            document.add(new Paragraph("\nUżytkownik: " + email, normalFont));
            document.add(new Paragraph("\nKwota: " + reservation.getCost(), normalFont));

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

