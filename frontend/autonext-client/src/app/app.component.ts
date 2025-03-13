import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { SWAGGER } from './config';
import { HeaderComponent } from "./shared/header/header.component";
import { MapsComponent } from "./maps/maps/maps.component";

@Component({
  selector: 'app-root',
  imports: [RouterModule, RouterOutlet, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'autonext-client';

  public swagger: string = SWAGGER;


}
