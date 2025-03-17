import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IncidentsPageComponent } from './incidents-page.component';

describe('IncidentsPageComponent', () => {
  let component: IncidentsPageComponent;
  let fixture: ComponentFixture<IncidentsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IncidentsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IncidentsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
