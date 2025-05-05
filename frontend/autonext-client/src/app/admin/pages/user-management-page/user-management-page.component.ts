import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  imports: [ReactiveFormsModule],
  templateUrl: './user-management-page.component.html',
  styleUrl: './user-management-page.component.css'
})
export class UserManagementPageComponent {

  private fb = inject(FormBuilder);

  myForm = this.fb.group({
    email: [''],
  })

}
