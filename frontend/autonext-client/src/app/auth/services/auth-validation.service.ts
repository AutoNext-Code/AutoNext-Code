import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthValidationService {

  validateLoginFields(email: string, password: string): string | null {
    if (!email.trim() || !password.trim()) {
      return 'Por favor, ingrese su correo y contraseña.';
    }

    if (!this.isValidEmail(email)) {
      return 'El formato de email es incorrecto.';
    }

    return null;
  }

  validateRegisterFields(name: string, surname: string, email: string, password: string): string | null {
    if (!name.trim() || !surname.trim() || !email.trim() || !password.trim()) {
      return 'Todos los campos son obligatorios.';
    }

    if (!this.isValidEmail(email)) {
      return 'El formato de email es incorrecto.';
    }

    if (!this.isValidPassword(password)) {
      return 'La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una minúscula y un número.';
    }

    return null;
  }


  private isValidEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
    return emailRegex.test(email);
  }


  private isValidPassword(password: string): boolean {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    return passwordRegex.test(password);
  }
}
