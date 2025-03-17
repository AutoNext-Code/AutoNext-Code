import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceManagementPageComponent } from './place-management-page.component';

describe('PlaceManagementPageComponent', () => {
  let component: PlaceManagementPageComponent;
  let fixture: ComponentFixture<PlaceManagementPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlaceManagementPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlaceManagementPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
