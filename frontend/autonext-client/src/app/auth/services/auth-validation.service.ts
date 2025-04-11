import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthValidationService {

  private validateRequiredFields(
    fields: Record<string, string>
  ): string | null {
    const emptyField = Object.entries(fields).find(
      ([_, value]) => !value.trim()
    );
    return emptyField ? 'Todos los campos son obligatorios.' : null;
  }

  private isValidEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  private isValidPassword(password: string): boolean {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    return passwordRegex.test(password);
  }

  
  validateEmail(email: string): string | null {
    return this.isValidEmail(email)
      ? null
      : 'El formato del email no es válido.';
  }

  validatePassword(password: string): string | null {
    return this.isValidPassword(password)
      ? null
      : 'La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas y números.';
  }

  validateLoginFields(email: string, password: string): string | null {
    return (
      this.validateRequiredFields({ email, password }) ??
      this.validateEmail(email) ??
      null
    );
  }

  validateRegisterFields(
    name: string,
    surname: string,
    email: string,
    password: string,
    carPlate: string
  ): string | null {
    return (
      this.validateRequiredFields({
        name,
        surname,
        email,
        password,
        carPlate,
      }) ??
      this.validateEmail(email) ??
      this.validatePassword(password) ??
      null
    );
  }

  validateSwitchFields(email: string, password: string): string | null {
    return this.validateEmail(email) ?? this.validatePassword(password) ?? null;
  }
}
