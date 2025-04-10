import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordChangerComponent } from './password-changer.component';

describe('PasswordChangerComponent', () => {
  let component: PasswordChangerComponent;
  let fixture: ComponentFixture<PasswordChangerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PasswordChangerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasswordChangerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
