import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavAdminComponent } from './nav-admin';

describe('NavAdmin', () => {
  let component: NavAdminComponent;
  let fixture: ComponentFixture<NavAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
