import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpaceDataComponent } from './space-data.component';

describe('SpaceDataComponent', () => {
  let component: SpaceDataComponent;
  let fixture: ComponentFixture<SpaceDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpaceDataComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpaceDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
