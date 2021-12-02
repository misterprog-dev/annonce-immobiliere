import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAds } from 'src/app/modules/ads/list-ads/list-ads';

describe('ListAdsComponent', () => {
  let component: ListAds;
  let fixture: ComponentFixture<ListAds>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListAds ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListAds);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
