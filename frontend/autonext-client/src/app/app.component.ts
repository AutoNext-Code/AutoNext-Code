import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SWAGGER } from './config';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'autonext-client';

  public swagger: string = SWAGGER;


}
