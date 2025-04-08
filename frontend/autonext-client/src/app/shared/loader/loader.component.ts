import { OnInit, Component, inject, OnChanges, Input } from '@angular/core';
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
  

  ngOnChanges(): void {
    this.changeMap()
  }

  changeMap(): void {
    setTimeout(() => {
      
    }, 2000) ;
    setTimeout(() => {
      if(!this.isLoaded){
        this.appComponent.showToast("error", "Timeout de Carga", "La carga tardó demasiado") ;
      }
    },8000)
  }

}
