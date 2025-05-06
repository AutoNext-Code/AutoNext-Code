import { UsersDataComponent } from '@admin/components/users-data/users-data.component';
import { Component, inject, signal, WritableSignal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  imports: [ReactiveFormsModule, UsersDataComponent],
  templateUrl: './user-management-page.component.html',
  styleUrl: './user-management-page.component.css'
})
export class UserManagementPageComponent {

  private fb = inject(FormBuilder);

  emailSignal: WritableSignal<string> = signal('');

  myForm = this.fb.group({
    email: [''],
  });

  onSubmit(): void {
    const emailValue = this.myForm.value.email || '';
    this.emailSignal.set(emailValue);
  }

}
