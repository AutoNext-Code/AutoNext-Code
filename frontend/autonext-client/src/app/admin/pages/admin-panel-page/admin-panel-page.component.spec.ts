import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPanelPageComponent } from './admin-panel-page.component';

describe('AdminPanelPageComponent', () => {
  let component: AdminPanelPageComponent;
  let fixture: ComponentFixture<AdminPanelPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminPanelPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminPanelPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
