import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicesJobComponent } from './services-job';

describe('ServicesJob', () => {
  let component: ServicesJobComponent;
  let fixture: ComponentFixture<ServicesJobComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicesJobComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicesJobComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
