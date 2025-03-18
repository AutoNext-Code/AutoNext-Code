import { CommonModule, NgFor } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import {  FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'select-dep',
  imports: [CommonModule, NgFor, ReactiveFormsModule],
  templateUrl: './select-dep.component.html',
  styleUrl: './select-dep.component.css'
})
export class SelectDepComponent implements OnChanges{

  form = new FormGroup({
    cat: new FormControl(''),
    subcat: new FormControl('')
  });


  @Input() cats: string[] = [];
  @Input() subcats: {[key:string]:string[]} = {};
  @Input() catText: string = "Categoria";
  @Input() subCatText: string = "SubCategoria";

  subCatOptions: string[] = [];




  ngOnChanges(changes: SimpleChanges): void {
    if(changes['cats'] && this.cats.length > 0 && !this.form.get('cat')?.value){
      this.form.get('cat')?.setValue(this.cats[0]);
      this.updateSubCats();
    }

  }

  updateSubCats(){
    const catSelected = this.form.get('cat')?.value;
    this.subCatOptions = this.subcats[catSelected!]||[];
    this.form.get('subcat')?.setValue(this.subCatOptions[0]||'')
  }


}
