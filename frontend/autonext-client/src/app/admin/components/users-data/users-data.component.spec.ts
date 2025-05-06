import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersDataComponent } from './users-data.component';

describe('UsersDataComponent', () => {
  let component: UsersDataComponent;
  let fixture: ComponentFixture<UsersDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsersDataComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
