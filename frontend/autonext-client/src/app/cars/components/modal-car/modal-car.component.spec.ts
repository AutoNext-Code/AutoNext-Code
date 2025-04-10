import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCarComponent } from './modal-car.component';

describe('ModalCarComponent', () => {
  let component: ModalCarComponent;
  let fixture: ComponentFixture<ModalCarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalCarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
