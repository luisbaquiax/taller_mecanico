import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormRegisterClient } from './form-register-client';

describe('FormRegisterClient', () => {
  let component: FormRegisterClient;
  let fixture: ComponentFixture<FormRegisterClient>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormRegisterClient]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormRegisterClient);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
