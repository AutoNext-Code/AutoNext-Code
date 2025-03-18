import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectDepComponent } from './select-dep.component';

describe('SelectDepComponent', () => {
  let component: SelectDepComponent;
  let fixture: ComponentFixture<SelectDepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectDepComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectDepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
