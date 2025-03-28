import { Component } from '@angular/core';
import { HeaderComponent } from "../../../shared/header/header.component";
import { UserDataComponent } from "../../components/user-data/user-data.component";

@Component({
  imports: [HeaderComponent, UserDataComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

}
