import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartsJobComponent } from './parts-job';

describe('PartsJob', () => {
  let component: PartsJobComponent;
  let fixture: ComponentFixture<PartsJobComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartsJobComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PartsJobComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
