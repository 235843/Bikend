package bikend.utils.mail;

import bikend.domain.ReservationEntity;
import bikend.utils.pdf.PdfGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSender {

    private final JavaMailSender mailSender;
    private final String HOST_EMAIL = "bikend165@gmail.com";

    public void sendRentInvoice(String email, ReservationEntity reservation) {
        StringBuilder mess = new StringBuilder("Szanowny kliencie !\r\n");
        mess.append("W załączniku przesyłamy potwierdzenie rezerwacji.\r\n")
                .append("Dziękujemy za wybranie Bikend i widzimy się w przyszły weekend.");
        StringBuilder title = new StringBuilder("Potwierdzenie_rezerwacji_");
        title.append(reservation.getId()).append(".pdf");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setTo(email);
            helper.setSubject("Potwierdzenie rezerwacji Bikend");
            helper.setText(mess.toString());
            helper.setFrom(HOST_EMAIL);

            helper.addAttachment(title.toString(), new ByteArrayResource(PdfGenerator.generateRegistrationPdf(email, reservation)));

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendWelcomeMail(String email, String name, String link) {
        StringBuilder mess = new StringBuilder("Witaj ");
        mess.append(name)
                .append("!\r\n")
                .append("Dziękujemy za rejestrację w serwisie Bikend! Aby Aktywować konto skorzystaj z poniższego linku.\r\n")
                .append("http://localhost:8080/auth/verify?token=")
                .append(link)
                .append("\r\nŻyczymy szerokiej drogi!");

        sendMail(email, "Rejestracja w Bikend", mess.toString());
    }
    public void sendMail(String email, String subject, String mess) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(mess);
        mailMessage.setFrom(HOST_EMAIL);

        mailSender.send(mailMessage);
    }
}
