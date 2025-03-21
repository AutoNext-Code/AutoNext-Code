import { CommonModule, NgFor } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
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

  @Output() catSubCatSelected = new EventEmitter<{catSelected:string, subCatSelected:string}>();

  subCatOptions: string[] = [];




  ngOnChanges(changes: SimpleChanges): void {
    if(changes['cats'] && this.cats.length > 0 && !this.form.get('cat')?.value){
      this.form.get('cat')?.setValue(this.cats[0]);
      this.updateSubCats();
      this.emitSubCat();
    }

  }

  updateSubCats(){
    const catSelected = this.form.get('cat')?.value;
    const subCatSelected = this.form.get('subcat')?.value;
    this.subCatOptions = this.subcats[catSelected!]||[];
    this.form.get('subcat')?.setValue(this.subCatOptions[0]||'')

    this.emitSubCat();

  }

  onSubcategoryChange(){
    this.emitSubCat();
  }

  emitSubCat(){
    const catSelected = this.form.get('cat')?.value;
    const subCatSelected = this.form.get('subcat')?.value;

    if (catSelected && subCatSelected) {
      this.catSubCatSelected.emit({ catSelected, subCatSelected });
    }
  }



}
