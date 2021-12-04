import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAds } from 'src/app/modules/ads/list-ads/list-ads';
import { configureTestSuite } from 'ng-bullet';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { AnnonceService } from 'src/app/shared/services/annonce.service';
import { MessageService } from 'primeng/api';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';

describe('ListAds', () => {
	let component: ListAds;
	let fixture: ComponentFixture<ListAds>;
	// @ts-ignore
	let annonceServiceStub: any;
	let messageServiceStub;
	const annonce1 = new Annonce();
	const annonce2 = new Annonce();

	configureTestSuite(() => {
		messageServiceStub = jasmine.createSpyObj('MessageService', ['add']);
		annonceServiceStub = jasmine.createSpyObj('AnnonceService', ['readAds']);

		TestBed.configureTestingModule({
			imports: [
				HttpClientTestingModule,
				ButtonModule,
				ToastModule,
				TableModule,
				DialogModule
			],
			providers: [
				{ provide: AnnonceService, useValue: annonceServiceStub },
				{ provide: MessageService, useValue: messageServiceStub }
			],
			schemas: [NO_ERRORS_SCHEMA]
		})
				.compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(ListAds);
		component = fixture.componentInstance;
		spyOn(component, 'readAds');
		// @ts-ignore
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should test readAds', function() {
		// GIVEN
		annonceServiceStub.readAds.and.returnValue(of([annonce1, annonce2]));

		// WHEN
		component.readAds();

		// THEN
		expect(component.annoncesSelectionnes).toEqual([]);
	});

	it('should tester onAdsChanged', function() {
		// WHEN
		component.onAdsChanged();

		// THEN
		expect(component.readAds).toHaveBeenCalled();
		expect(component.annoncesSelectionnes).toHaveSize(0);
		expect(component.annonceSelectionne).toBeNull();
	});

	it('should tester openEditingModal cas création', () => {
		// WHEN
		component.openEditingModal();

		// THEN
		expect(component.editingVisible).toBeTruthy();
		expect(component.annonceSelectionne).toBeUndefined();
	});

	it('should tester openEditingModal cas modification', () => {
		// WHEN
		component.openEditingModal(annonce1);

		// THEN
		expect(component.editingVisible).toBeTruthy();
		expect(component.annonceSelectionne).toEqual(annonce1);
	});

	it('should tester openDeletingModal', () => {
		// WHEN
		component.openDeletingModal(annonce1);

		// THEN
		expect(component.deleteVisible).toBeTruthy();
		expect(component.annonceSelectionne).toEqual(annonce1);
	});

	it('should tester update list after operation', () => {
		// WHEN
		component.onAdsChanged();

		// THEN
		expect(component.readAds).toHaveBeenCalled();
		expect(component.annonceSelectionne).toBeNull();
		expect(component.annoncesSelectionnes).toEqual([]);
	});
});
