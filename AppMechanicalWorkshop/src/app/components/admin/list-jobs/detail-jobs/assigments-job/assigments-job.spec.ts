import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssigmentsJobComponent } from './assigments-job';

describe('AssigmentsJob', () => {
  let component: AssigmentsJobComponent;
  let fixture: ComponentFixture<AssigmentsJobComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssigmentsJobComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssigmentsJobComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
