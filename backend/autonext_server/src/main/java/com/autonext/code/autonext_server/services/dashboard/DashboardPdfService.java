package com.autonext.code.autonext_server.services.dashboard;

import com.autonext.code.autonext_server.dto.dashboardDtos.DashboardExportRequest;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class DashboardPdfService {

  @Autowired
  private UserRepository userRepository;

  public byte[] generatePdf(DashboardExportRequest request) {
    try {
      User user = getAuthenticatedUser();
      String fullName = user.getName() + " " + user.getSurname();

      Document document = new Document();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      PdfWriter.getInstance(document, out);
      document.open();

      // Fuente personalizada
      BaseFont baseFont = BaseFont.createFont(
          "src/main/resources/fonts/IBMPlexSerif-Regular.ttf",
          BaseFont.IDENTITY_H,
          BaseFont.EMBEDDED);
      Font normalFont = new Font(baseFont, 12, Font.NORMAL);
      Font boldFont = new Font(baseFont, 18, Font.BOLD);

      // Logo
      Paragraph header = new Paragraph();
      Image logo = Image.getInstance("src/main/resources/static/autonext-logo.png");
      logo.scaleToFit(50, 30);
      logo.setAlignment(Element.ALIGN_CENTER);

      Chunk auto = new Chunk("Auto", new Font(baseFont, 18, Font.BOLD, new BaseColor(24, 181, 217)));
      Chunk next = new Chunk("Next", new Font(baseFont, 18, Font.BOLD, new BaseColor(0, 103, 184)));

      Paragraph branding = new Paragraph();
      branding.add(auto);
      branding.add(next);
      branding.setAlignment(Element.ALIGN_CENTER);

      header.add(logo);
      header.add(Chunk.NEWLINE);
      header.add(branding);
      header.setAlignment(Element.ALIGN_CENTER);
      header.setSpacingAfter(10f);

      document.add(header);

      // Título principal
      String periodo = (request.getMonth() != null)
          ? String.format("%02d/%d", request.getMonth(), request.getYear())
          : String.format("Año %d", request.getYear());

      String titulo = String.format("Resumen de Uso - %s para %s", periodo, fullName);

      Paragraph title = new Paragraph(titulo, boldFont);
      title.setAlignment(Element.ALIGN_CENTER);
      title.setSpacingBefore(20);
      title.setSpacingAfter(20);
      document.add(title);

      Paragraph strikeResumen = new Paragraph();
      strikeResumen.setSpacingBefore(10f);
      strikeResumen.setSpacingAfter(10f);
      strikeResumen.setAlignment(Element.ALIGN_LEFT);
      strikeResumen.setIndentationLeft(50f);

      strikeResumen.add(new Phrase("Estado del usuario:\n", new Font(baseFont, 14, Font.BOLD, BaseColor.DARK_GRAY)));
      strikeResumen.add(new Phrase(String.format("• Strikes: %d\n", request.getStrikes()), normalFont));

      Font greenFont = new Font(baseFont, 12, Font.BOLD, BaseColor.GREEN);
      Font redFont = new Font(baseFont, 12, Font.BOLD, BaseColor.RED);

      strikeResumen.add(new Phrase("• ¿Usuario baneado?: ", normalFont));

      if (request.isBanned()) {
        strikeResumen.add(new Phrase("Sí\n", redFont));
      } else {
        strikeResumen.add(new Phrase("No\n", greenFont));
      }

      document.add(strikeResumen);

      // Gráficas
      addChartRow(
          document,
          request.getDaysPerMonthChart(), "Días reservados por mes", "Total",
          String.valueOf(request.getTotalDaysReserved()),
          request.getHoursPerMonthChart(), "Horas reservadas por mes", "Total",
          String.valueOf(request.getTotalHoursReserved()),
          normalFont);

      addChartRow(
          document,
          request.getAvgDurationPerMonthChart(), "Duración media de la reserva por mes", "Promedio",
          String.format("%.2f horas", request.getAverageSessionDuration()),
          request.getHoursPerWeekdayChart(), "Horas de media por día a la semana", "Total",
          String.valueOf(request.getTotalWeeklyHoursReserved()),
          normalFont);

      addChartRow(
          document,
          request.getUnconfirmedChart(), "Reservas no confirmadas por mes", "Total",
          String.valueOf(request.getUnconfirmedReservations()),
          request.getConfirmationsChart(), "Reservas confirmadas por mes", "Total",
          String.valueOf(request.getConfirmedReservations()),

          normalFont);

      // Pie de página
      Paragraph spacer = new Paragraph();
      spacer.setSpacingBefore(450f); 
      document.add(spacer);

      Paragraph footer = new Paragraph(
          String.format("Exportado por %s el %s", fullName,
              LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
          new Font(baseFont, 10, Font.ITALIC, BaseColor.GRAY));
      footer.setAlignment(Element.ALIGN_RIGHT);
      footer.setSpacingBefore(30f);
      document.add(footer);

      document.close();
      return out.toByteArray();

    } catch (Exception e) {
      System.err.println(">>> ERROR GENERANDO PDF: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Error generating dashboard PDF", e);
    }
  }

  private void addChartRow(Document document, String base64Image1, String title1, String totalLabel1,
      String totalValue1,
      String base64Image2, String title2, String totalLabel2, String totalValue2,
      Font font) throws Exception {
    PdfPTable table = new PdfPTable(2);
    table.setWidthPercentage(100);
    table.setSpacingBefore(20f);
    table.setSpacingAfter(20f);
    table.setWidths(new float[] { 1, 1 });

    table.addCell(createChartCell(base64Image1, title1, totalLabel1, totalValue1, font));
    table.addCell(createChartCell(base64Image2, title2, totalLabel2, totalValue2, font));

    document.add(table);
  }

  private PdfPCell createChartCell(String base64, String title, String totalLabel, String totalValue, Font font)
      throws Exception {
    PdfPCell cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);

    // Título en cursiva
    Font italicFont = new Font(font.getBaseFont(), 12, Font.ITALIC, BaseColor.BLACK);
    Paragraph titleP = new Paragraph(title, italicFont);
    titleP.setAlignment(Element.ALIGN_CENTER);
    titleP.setSpacingAfter(5f);
    cell.addElement(titleP);

    // Imagen
    if (base64 != null && !base64.isEmpty()) {
      Image img = Image.getInstance(Base64.getDecoder().decode(base64.split(",")[1]));
      img.scaleToFit(240, 180);
      img.setAlignment(Image.ALIGN_CENTER);
      cell.addElement(img);
    }

    // Total personalizado
    if (totalLabel != null && !totalLabel.isEmpty() && totalValue != null && !totalValue.isEmpty()) {
      String textoBase = title.toLowerCase();
      String pluralizado = totalValue.equals("1")
          ? textoBase.replace("reservadas", "reservada").replace("reservados", "reservado")
              .replace("reservas", "reserva").replace("confirmadas", "confirmada")
              .replace("no confirmadas", "no confirmada")
              .replace("días", "día").replace("horas", "hora")
          : textoBase;
      String totalTexto = String.format("Total: %s %s", totalValue, pluralizado);
      Font smallFont = new Font(font.getBaseFont(), 10, Font.NORMAL, BaseColor.BLACK);
      Paragraph totalP = new Paragraph(totalTexto, smallFont);
      totalP.setAlignment(Element.ALIGN_CENTER);
      totalP.setSpacingBefore(5f);
      cell.addElement(totalP);
    }

    return cell;
  }

  private User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();

    if (principal instanceof User user) {
      return user;
    } else if (principal instanceof String email) {
      return userRepository.findByEmail(email)
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    throw new SecurityException("Usuario no autenticado correctamente");
  }

}