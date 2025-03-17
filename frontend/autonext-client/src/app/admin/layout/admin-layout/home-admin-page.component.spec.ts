import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeAdminPageComponent } from './home-admin-page.component';

describe('HomeAdminPageComponent', () => {
  let component: HomeAdminPageComponent;
  let fixture: ComponentFixture<HomeAdminPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeAdminPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeAdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
