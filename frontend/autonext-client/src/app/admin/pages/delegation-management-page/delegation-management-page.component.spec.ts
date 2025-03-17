import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DelegationManagementPageComponent } from './delegation-management-page.component';

describe('DelegationManagementPageComponent', () => {
  let component: DelegationManagementPageComponent;
  let fixture: ComponentFixture<DelegationManagementPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DelegationManagementPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DelegationManagementPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
