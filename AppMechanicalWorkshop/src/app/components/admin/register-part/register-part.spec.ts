import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterPart } from './register-part';

describe('RegisterPart', () => {
  let component: RegisterPart;
  let fixture: ComponentFixture<RegisterPart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterPart]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterPart);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
