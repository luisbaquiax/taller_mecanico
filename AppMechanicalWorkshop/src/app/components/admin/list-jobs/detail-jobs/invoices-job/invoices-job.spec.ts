import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvoicesJobComponent } from './invoices-job';

describe('InvoicesJob', () => {
  let component: InvoicesJobComponent;
  let fixture: ComponentFixture<InvoicesJobComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InvoicesJobComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvoicesJobComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
