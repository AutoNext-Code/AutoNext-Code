package com.autonext.code.autonext_server.services;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.MapBookingDTO;
import com.autonext.code.autonext_server.exceptions.EmailSendingException;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;

import jakarta.mail.MessagingException;

@Service
public class EmailTemplateService {

  @Value("${url.client}")
  private String clientUrl;

  @Autowired
  private EmailSenderService emailSenderService;

  public void notifyUserOnBookingCreation(Booking booking, MapBookingDTO mapBookingDTO) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String htmlContent = String.format(
        """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 20px;
                    }
                    .container {
                        background-color: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 10px;
                        padding: 20px;
                        max-width: 600px;
                        margin: auto;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                    }
                    h2 {
                        color: #0067B8;
                        margin-bottom: 10px;
                    }
                    p {
                        color: #333333;
                    }
                    ul {
                        list-style: none;
                        padding: 0;
                    }
                    li {
                        padding: 5px 0;
                        color: #555;
                    }
                    li strong {
                        color: #0067B8;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 0.9em;
                        color: #888;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>¡Tu reserva ha sido creada con éxito!</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Gracias por usar <strong>AutoNext</strong>. Tu reserva ha sido registrada con los siguientes detalles:</p>
                    <ul>
                        <li><strong>Fecha:</strong> %s</li>
                        <li><strong>Hora de inicio:</strong> %s</li>
                        <li><strong>Hora de fin:</strong> %s</li>
                        <li><strong>Coche:</strong> %s</li>
                        <li><strong>Plaza de aparcamiento:</strong> %s</li>
                        <li><strong>Centro de trabajo:</strong> %s</li>
                    </ul>
                    <p>Se te avisará antes de que comience tu reserva.</p>
                    <div class="footer">
                        <p>AutoNext &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
            """,
        booking.getUser().getName(),
        mapBookingDTO.getDate().format(formatter),
        mapBookingDTO.getStartTime(),
        mapBookingDTO.getEndTime(),
        booking.getCar().getCarPlate(),
        booking.getParkingSpace().getName(),
        booking.getWorkCenter().getName());

    try {
      this.emailSenderService.sendHtmlEmail(
          booking.getUser().getEmail(),
          "Reserva creada con éxito",
          htmlContent);
    } catch (MessagingException e) {
      throw new EmailSendingException("Error al enviar el correo de confirmación", e);
    }
  }

  public void notifyUserOnBookingCancellation(Booking booking) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    String htmlContent = String.format(
        """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 20px;
                    }
                    .container {
                        background-color: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 10px;
                        padding: 20px;
                        max-width: 500px;
                        margin: auto;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                        color: #333;
                    }
                    h2 {
                        color: #0067B8;
                        margin-bottom: 10px;
                    }
                    p {
                        color: #333;
                        line-height: 1.5;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 0.85em;
                        color: #888;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Reserva cancelada</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Te informamos que tu reserva para el día <strong>%s</strong>, desde las <strong>%s</strong> hasta las <strong>%s</strong>, en el puesto <strong>%s</strong> ha sido cancelada correctamente.</p>
                    <p>Si esto fue un error o necesitas ayuda, no dude en contactarnos.</p>
                    <div class="footer">
                        <p>AutoNext &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
            """,
        booking.getUser().getName(),
        booking.getDate().format(dateFormatter),
        booking.getStartTime().format(timeFormatter),
        booking.getEndTime().format(timeFormatter),
        booking.getParkingSpace().getName());

    try {
      this.emailSenderService.sendHtmlEmail(
          booking.getUser().getEmail(),
          "Su reserva ha sido cancelada",
          htmlContent);
    } catch (MessagingException e) {
      throw new EmailSendingException("Error al enviar el correo de cancelación", e);
    }
  }

  public void notifyUserOnAdminCancellation(Booking booking) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    String htmlContent = String.format(
        """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 20px;
                    }
                    .container {
                        background-color: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 10px;
                        padding: 20px;
                        max-width: 500px;
                        margin: auto;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                        color: #333;
                    }
                    h2 {
                        color: #0067B8;
                        margin-bottom: 10px;
                    }
                    p {
                        color: #333;
                        line-height: 1.5;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 0.85em;
                        color: #888;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Reserva cancelada</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Te informamos que tu reserva para el día <strong>%s</strong>, desde las <strong>%s</strong> hasta las <strong>%s</strong>, en el puesto <strong>%s</strong> ha sido cancelada debido a cambios en sus características.</p>
                    <p>Lamentamos las molestias.</p>
                    <div class="footer">
                        <p>AutoNext &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
            """,
        booking.getUser().getName(),
        booking.getDate().format(dateFormatter),
        booking.getStartTime().format(timeFormatter),
        booking.getEndTime().format(timeFormatter),
        booking.getParkingSpace().getName());

    try {
      this.emailSenderService.sendHtmlEmail(
          booking.getUser().getEmail(),
          "Su reserva ha sido cancelada",
          htmlContent);
    } catch (MessagingException e) {
      throw new EmailSendingException("Error al enviar el correo de cancelación", e);
    }
  }

  public void sendReservationEndNotification(Booking booking) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    String htmlContent = String.format(
        """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 20px;
                    }
                    .container {
                        background-color: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 10px;
                        padding: 20px;
                        max-width: 500px;
                        margin: auto;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                        color: #333;
                    }
                    h2 {
                        color: #0067B8;
                        margin-bottom: 10px;
                    }
                    p {
                        color: #333;
                        line-height: 1.5;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 0.85em;
                        color: #888;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Tu reserva está por finalizar</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Queremos recordarte que tu reserva en <strong>%s</strong> termina a las <strong>%s</strong> del día <strong>%s</strong>.</p>
                    <p>Por favor, asegúrate de liberar el espacio a tiempo.</p>
                    <div class="footer">
                        <p>AutoNext &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
            """,
        booking.getUser().getName(),
        booking.getParkingSpace().getName(),
        booking.getEndTime(),
        booking.getDate().format(formatter));

    try {
      this.emailSenderService.sendHtmlEmail(
          booking.getUser().getEmail(),
          "Tu reserva está por finalizar",
          htmlContent);
    } catch (MessagingException e) {
      throw new EmailSendingException("Error al enviar el correo de fin de reserva", e);
    }

  }

  public void notifyUserOnReservationStart(Booking booking) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    String htmlContent = String.format(
        """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 20px;
                    }
                    .container {
                        background-color: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 10px;
                        padding: 20px;
                        max-width: 500px;
                        margin: auto;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                        color: #333;
                    }
                    h2 {
                        color: #0067B8;
                        margin-bottom: 10px;
                    }
                    p {
                        color: #333;
                        line-height: 1.5;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 0.85em;
                        color: #888;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Tu reserva está por comenzar</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Te recordamos que tu reserva en <strong>%s</strong> comienza a las <strong>%s</strong> del día <strong>%s</strong>.</p>
                    <p>Por favor, no olvides confirmar tu llegada en la app.</p>
                    <div class="footer">
                        <p>AutoNext &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
            """,
        booking.getUser().getName(),
        booking.getParkingSpace().getName(),
        booking.getStartTime(),
        booking.getDate().format(formatter));

    try {
      this.emailSenderService.sendHtmlEmail(
          booking.getUser().getEmail(),
          "Tu reserva está por comenzar",
          htmlContent);
    } catch (MessagingException e) {
      throw new EmailSendingException("Error al enviar el correo de inicio de reserva", e);
    }
  }

  public void sendResetPasswordEmail(String email, String token) {
    String confirmationLink = clientUrl + "/auth/reset-password/" + token;
    String htmlContent = String.format("""
            <html>
                <head>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f9f9f9;
                            padding: 20px;
                        }
                        .container {
                            background-color: #ffffff;
                            border: 1px solid #e0e0e0;
                            border-radius: 10px;
                            padding: 20px;
                            max-width: 500px;
                            margin: auto;
                            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                        }
                        h1 {
                            color: #0067B8;
                        }
                        p {
                            color: #333;
                        }
                        a {
                            color: #0067B8;
                            text-decoration: none;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Restablecer tu contraseña</h1>
                        <p>Para restablecer tu contraseña, por favor, haz clic en el siguiente enlace:</p>
                        <a href="%s">Restablecer contraseña</a>
                    </div>
                </body>
            </html>
        """,
        confirmationLink);

    try {
      this.emailSenderService.sendHtmlEmail(email, "Restablecimiento de contraseña", htmlContent);
    } catch (MessagingException e) {
      throw new EmailSendingException("Error al enviar el correo de restablecimiento de contraseña", e);
    }
  }

  public void notifyUserStrikeForNoConfirmation(Booking booking) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    String htmlContent = String.format(
        """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 20px;
                    }
                    .container {
                        background-color: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 10px;
                        padding: 20px;
                        max-width: 600px;
                        margin: auto;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                        color: #333;
                    }
                    h2 {
                        color: #d32f2f;
                        margin-bottom: 10px;
                    }
                    p {
                        line-height: 1.5;
                    }
                    ul {
                        list-style: none;
                        padding: 0;
                    }
                    li {
                        padding: 5px 0;
                    }
                    li strong {
                        color: #0067B8;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 0.85em;
                        color: #888;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Has recibido un strike por no confirmar tu reserva</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Te informamos que se te ha aplicado un <strong>strike</strong> por no confirmar tu asistencia a la siguiente reserva:</p>
                    <ul>
                        <li><strong>Fecha:</strong> %s</li>
                        <li><strong>Hora de inicio:</strong> %s</li>
                        <li><strong>Hora de fin:</strong> %s</li>
                        <li><strong>Coche:</strong> %s</li>
                        <li><strong>Plaza de aparcamiento:</strong> %s</li>
                        <li><strong>Centro de trabajo:</strong> %s</li>
                    </ul>
                    <p>Por favor, asegúrate de confirmar tus reservas futuras para evitar más strikes.</p>
                    <div class="footer">
                        <p>AutoNext &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
            """,
        booking.getUser().getName(),
        booking.getDate().format(dateFormatter),
        booking.getStartTime().format(timeFormatter),
        booking.getEndTime().format(timeFormatter),
        booking.getCar().getCarPlate(),
        booking.getParkingSpace().getName(),
        booking.getWorkCenter().getName());

    try {
      this.emailSenderService.sendHtmlEmail(
          booking.getUser().getEmail(),
          "Strike por no confirmar tu reserva",
          htmlContent);
    } catch (MessagingException e) {
      throw new EmailSendingException("Error al enviar el correo de strike", e);
    }
  }

  public void notifyUserPenaltyForAccumulatedStrikes(User user) {
    String htmlContent = String.format(
        """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 20px;
                    }
                    .container {
                        background-color: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 10px;
                        padding: 20px;
                        max-width: 600px;
                        margin: auto;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                        color: #333;
                    }
                    h2 {
                        color: #d32f2f;
                        margin-bottom: 10px;
                    }
                    p {
                        line-height: 1.5;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 0.85em;
                        color: #888;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Penalización de 15 días por acumulación de strikes</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Al haber acumulado <strong>3 strikes</strong> sin confirmar tus reservas, se te ha aplicado una penalización de <strong>15 días</strong> en tu cuenta.</p>
                    <p>Durante este periodo no podrás realizar ninguna reserva. Por favor, asegúrate de confirmar tus futuras reservas para evitar más penalizaciones.</p>
                    <div class="footer">
                        <p>AutoNext &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
            """,
        user.getName());

    try {
      this.emailSenderService.sendHtmlEmail(
          user.getEmail(),
          "Penalización de 15 días por acumulación de strikes",
          htmlContent);
    } catch (MessagingException e) {
      throw new EmailSendingException("Error al enviar el correo de penalización", e);
    }
  }

}
