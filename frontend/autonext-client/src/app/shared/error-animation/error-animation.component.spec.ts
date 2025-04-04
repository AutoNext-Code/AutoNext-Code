import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorAnimationComponent } from './error-animation.component';

describe('ErrorAnimationComponent', () => {
  let component: ErrorAnimationComponent;
  let fixture: ComponentFixture<ErrorAnimationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ErrorAnimationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ErrorAnimationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
