import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailCofirmationComponent } from './email-cofirmation.component';

describe('EmailCofirmationComponent', () => {
  let component: EmailCofirmationComponent;
  let fixture: ComponentFixture<EmailCofirmationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmailCofirmationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmailCofirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
