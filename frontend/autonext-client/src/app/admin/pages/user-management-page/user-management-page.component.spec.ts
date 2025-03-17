import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserManagementPageComponent } from './user-management-page.component';

describe('UserManagementPageComponent', () => {
  let component: UserManagementPageComponent;
  let fixture: ComponentFixture<UserManagementPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserManagementPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserManagementPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
