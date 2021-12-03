import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAdModal } from 'src/app/modules/ads/edit-ad/edit-ad.modal';

describe('AddAdComponent', () => {
  let component: EditAdModal;
  let fixture: ComponentFixture<EditAdModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditAdModal ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditAdModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
