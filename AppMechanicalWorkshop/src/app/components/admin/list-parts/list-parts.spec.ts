import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListParts } from './list-parts';

describe('ListParts', () => {
  let component: ListParts;
  let fixture: ComponentFixture<ListParts>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListParts]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListParts);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
