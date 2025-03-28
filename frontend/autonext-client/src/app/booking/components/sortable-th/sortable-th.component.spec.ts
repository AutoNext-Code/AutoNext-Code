import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SortableThComponent } from './sortable-th.component';

describe('SortableThComponent', () => {
  let component: SortableThComponent;
  let fixture: ComponentFixture<SortableThComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SortableThComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SortableThComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
