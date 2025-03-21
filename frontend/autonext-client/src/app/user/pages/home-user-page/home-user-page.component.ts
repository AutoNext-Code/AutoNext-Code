import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { MapsComponent } from '@maps/pages/maps.component';

@Component({
  imports: [HeaderComponent, MapsComponent],
  templateUrl: './home-user-page.component.html',
  styleUrl: './home-user-page.component.css'
})
export class HomeUserPageComponent {

}
