import { Component } from '@angular/core';

@Component({
  selector: 'profile-user-data',
  imports: [],
  templateUrl: './user-data.component.html',
  styleUrl: './user-data.component.css'
})
export class UserDataComponent {

  user = {
    name: "User user",
    email: "user@example.com",
    jobPosition: "developer",
    workCenterName: "Málaga",
    strikes: 1,
  }


}
