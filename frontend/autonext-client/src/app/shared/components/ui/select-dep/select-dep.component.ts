import { CommonModule, NgFor } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'select-dep',
  standalone: true,
  imports: [CommonModule, NgFor, ReactiveFormsModule],
  templateUrl: './select-dep.component.html',
  styleUrls: ['./select-dep.component.css']
})
export class SelectDepComponent implements OnChanges {
  form = new FormGroup({
    cat: new FormControl<string | null>(null),
    // ahora guardamos solo el id
    subcat: new FormControl<number | null>(null)
  });

  @Input() cats: string[] = [];
  @Input() subcats: { [key: string]: { number: string; id: number }[] } = {};
  @Input() catText: string = 'Categoria';
  @Input() subCatText: string = 'SubCategoria';

  // emitimos solo el id
  @Output() catSubCatSelected = new EventEmitter<number>();

  subCatOptions: { number: string; id: number }[] = [];

  ngOnChanges(changes: SimpleChanges): void {
    if (
      changes['cats'] &&
      this.cats.length > 0 &&
      !this.form.get('cat')?.value
    ) {
      this.form.get('cat')?.setValue(this.cats[0]);
      this.updateSubCats();
      this.emitSubCat();
    }
  }

  updateSubCats(): void {
    const catSelected = this.form.get('cat')!.value!;
    this.subCatOptions = this.subcats[catSelected] ?? [];
    // inicializamos con el primer id o null
    const firstId = this.subCatOptions.length > 0 ? this.subCatOptions[0].id : null;
    this.form.get('subcat')?.setValue(firstId);
    this.emitSubCat();
  }

  onSubcategoryChange(): void {
    this.emitSubCat();
  }

  private emitSubCat(): void {
    const subCatSelected = this.form.get('subcat')!.value;
    if (subCatSelected !== null) {
      this.catSubCatSelected.emit(subCatSelected);
    }
  }
}
