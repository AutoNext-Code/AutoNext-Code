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
          : String.format("todo el año %d", request.getYear());

      String titulo = String.format("Resumen de uso - %s para %s", periodo, fullName);

      Paragraph title = new Paragraph(titulo, boldFont);
      title.setAlignment(Element.ALIGN_CENTER);
      title.setSpacingBefore(20);
      title.setSpacingAfter(20);
      document.add(title);

      // Gráficas
      addImage(document, request.getDaysPerMonthChart(), "Días reservados por mes", normalFont);
      addImage(document, request.getHoursPerMonthChart(), "Horas reservadas por mes", normalFont);
      addImage(document, request.getAvgDurationPerMonthChart(), "Duración media por mes", normalFont);
      addImage(document, request.getHoursPerWeekdayChart(), "Horas por día de la semana", normalFont);
      addImage(document, request.getConfirmationsChart(), "Confirmaciones vs No confirmaciones", normalFont);

      document.close();
      return out.toByteArray();

    } catch (Exception e) {
      System.err.println(">>> ERROR GENERANDO PDF: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Error generating dashboard PDF", e);
    }

  }

  private void addImage(Document document, String base64, String title, Font font) throws Exception {
    if (base64 == null || base64.isEmpty())
      return;

    Image image = Image.getInstance(Base64.getDecoder().decode(base64.split(",")[1]));
    image.scaleToFit(500, 400);
    image.setAlignment(Element.ALIGN_CENTER);

    Paragraph label = new Paragraph(title, font);
    label.setAlignment(Element.ALIGN_CENTER);
    label.setSpacingBefore(10);
    label.setSpacingAfter(5);

    document.add(label);
    document.add(image);
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