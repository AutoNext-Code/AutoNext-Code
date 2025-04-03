import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationAnimationComponent } from './confirmation-animation.component';

describe('ConfirmationAnimationComponent', () => {
  let component: ConfirmationAnimationComponent;
  let fixture: ComponentFixture<ConfirmationAnimationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmationAnimationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmationAnimationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
