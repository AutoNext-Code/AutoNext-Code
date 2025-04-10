import { OnInit, Component, inject, OnChanges, Input, SimpleChanges } from '@angular/core';
import { AppComponent } from '../../app.component';
 
 
@Component({
  selector: 'loader',
  imports: [],
  templateUrl: './loader.component.html',
  styleUrl: './loader.component.css'
})
export class LoaderComponent implements OnChanges {
 
  private appComponent: AppComponent = inject(AppComponent);
 
  @Input({required: true})
  isLoaded: boolean = false ;
 
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['isLoaded'] && !this.isLoaded) {
      this.changeMap();
    }
  }
 
  changeMap(): void {
    setTimeout(() => {
      if (!this.isLoaded) {
        this.appComponent.showToast("error", "Timeout de Carga", "La carga tardó demasiado");
      }
    }, 8000);
  }
 
 
}
 