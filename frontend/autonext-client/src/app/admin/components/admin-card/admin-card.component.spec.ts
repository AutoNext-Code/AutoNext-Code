import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCardComponent } from './admin-card.component';

describe('AdminCardComponent', () => {
  let component: AdminCardComponent;
  let fixture: ComponentFixture<AdminCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
