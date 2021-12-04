import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAdModal } from 'src/app/modules/ads/delete-ad/delete-ad.modal';

describe('DeleteAdComponent', () => {
  let component: DeleteAdModal;
  let fixture: ComponentFixture<DeleteAdModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteAdModal ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteAdModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
