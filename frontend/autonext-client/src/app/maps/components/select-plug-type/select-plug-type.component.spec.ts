import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectPlugTypeComponent } from './select-plug-type.component';

describe('SelectPlugTypeComponent', () => {
  let component: SelectPlugTypeComponent;
  let fixture: ComponentFixture<SelectPlugTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectPlugTypeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectPlugTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
