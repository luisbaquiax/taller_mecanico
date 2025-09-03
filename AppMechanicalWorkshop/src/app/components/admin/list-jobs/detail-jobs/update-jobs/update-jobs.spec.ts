import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateJobsComponent } from './update-jobs';

describe('UpdateJobs', () => {
  let component: UpdateJobsComponent;
  let fixture: ComponentFixture<UpdateJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateJobsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
