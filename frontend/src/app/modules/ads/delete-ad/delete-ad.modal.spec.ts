import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAdModal } from 'src/app/modules/ads/delete-ad/delete-ad.modal';
import { configureTestSuite } from 'ng-bullet';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { AnnonceService } from 'src/app/shared/services/annonce.service';
import { MessageService } from 'primeng/api';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { of } from 'rxjs';

describe('DeleteAdComponent', () => {
	let component: DeleteAdModal;
	let fixture: ComponentFixture<DeleteAdModal>;
	let annonceServiceStub: any;
	let messageServiceStub;

	configureTestSuite(() => {
		messageServiceStub = jasmine.createSpyObj('MessageService', ['add']);
		annonceServiceStub = jasmine.createSpyObj('AnnonceService', ['deleteAd']);

		TestBed.configureTestingModule({
			imports: [
				HttpClientTestingModule,
				ButtonModule,
				ToastModule,
				DialogModule,
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
		fixture = TestBed.createComponent(DeleteAdModal);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should tester open modal', () => {
		// GIVEN
		spyOn(component.visibleChange, 'emit');

		// WHEN
		component.openModal();

		// THEN
		expect(component.visibleChange.emit).toHaveBeenCalled();
	});

	it('should tester close modal', () => {
		// GIVEN
		spyOn(component.visibleChange, 'emit');

		// WHEN
		component.onClose();

		// THEN
		expect(component.visibleChange.emit).toHaveBeenCalled();
		expect(component.loading).toBeFalsy();
	});

	it('should tester supprimer', () => {
		// GIVEN
		spyOn(component, 'onClose');
		spyOn(component.annonceChange, 'emit');
		component.annonce = new Annonce();
		component.annonce.id = 1;
		annonceServiceStub.deleteAd.and.returnValue(of({}));

		// WHEN
		component.onSupprimer();

		// THEN
		expect(component.onClose).toHaveBeenCalled();
		expect(component.annonceChange.emit).toHaveBeenCalled();
		expect(annonceServiceStub.deleteAd).toHaveBeenCalled();
		expect(component.loading).toBeFalsy();
	});
});
