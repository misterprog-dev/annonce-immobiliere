import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAdModal } from 'src/app/modules/ads/edit-ad/edit-ad.modal';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { configureTestSuite } from 'ng-bullet';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { AnnonceService } from 'src/app/shared/services/annonce.service';
import { MessageService } from 'primeng/api';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { FileUploadModule } from 'primeng/fileupload';
import { of } from 'rxjs';

describe('AddAdComponent', () => {
	let component: EditAdModal;
	let fixture: ComponentFixture<EditAdModal>;
	let annonceServiceStub: any;
	let messageServiceStub;

	configureTestSuite(() => {
		messageServiceStub = jasmine.createSpyObj('MessageService', ['add']);
		annonceServiceStub = jasmine.createSpyObj('AnnonceService', ['readAd', 'getFile', 'createAd', 'updateAd']);

		TestBed.configureTestingModule({
			imports: [
				HttpClientTestingModule,
				ButtonModule,
				ToastModule,
				DialogModule,
				ReactiveFormsModule,
				InputTextModule,
				InputTextareaModule,
				FileUploadModule
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
		fixture = TestBed.createComponent(EditAdModal);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should tester open modal mode création', () => {
		// GIVEN
		spyOn(component.visibleChange, 'emit');

		// WHEN
		component.openModalEdition();

		// THEN
		expect(component.visibleChange.emit).toHaveBeenCalled();
	});

	it('should tester open modal mode édition', () => {
		// GIVEN
		spyOn(component.visibleChange, 'emit');
		spyOnProperty(component, 'isUpdating', 'get').and.returnValue(true);
		spyOn(component, 'activateUpdating');
		spyOn(component, 'readFile');

		// WHEN
		component.openModalEdition();

		// THEN
		expect(component.visibleChange.emit).toHaveBeenCalled();
		expect(component.isUpdating).toBeTruthy();
		expect(component.activateUpdating).toHaveBeenCalled();
		expect(component.readFile).toHaveBeenCalled();
	});

	it('should tester activer edition', () => {
		// GIVEN
		component.annonce = new Annonce();
		component.annonce.id = 1;
		annonceServiceStub.readAd.and.returnValue(of(new Annonce('A', 'B')));

		// WHEN
		component.activateUpdating();

		// THEN
		expect(component.formAd.get('title')?.value).toEqual('A');
	});

	it('should tester reading file for updating', () => {
		// GIVEN
		component.annonce = new Annonce();
		component.annonce.id = 1;
		component.annonce.fileName = 'image.png';
		annonceServiceStub.getFile.and.returnValue(of({}));

		// WHEN
		component.readFile();

		// THEN
		expect(component.uploadedFile).not.toBeUndefined();
	});

	it('should tester close modal', () => {
		// GIVEN
		spyOn(component.visibleChange, 'emit');
		spyOn(component.formAd, 'reset');

		// WHEN
		component.onClose();

		// THEN
		expect(component.visibleChange.emit).toHaveBeenCalled();
		expect(component.formAd.reset).toHaveBeenCalled();
		expect(component.loading).toBeFalsy();
		expect(component.uploadedFile).toBeUndefined();
	});

	it('should tester get modal mode title with annonce existant.', () => {
		// GIVEN
		component.annonce = new Annonce();

		// WHEN
		const title = component.getEditingModalTitle();

		// THEN
		expect(title).toEqual('Modification d\'une annonce');
	});

	it('should tester get modal mode title with annonce inexistant.', () => {
		// GIVEN
		// @ts-ignore
		component.annonce = undefined;

		// WHEN
		const title = component.getEditingModalTitle();

		// THEN
		expect(title).toEqual('Création d\'une annonce');
	});

	it('should tester save cas création.', () => {
		// GIVEN
		spyOn(component, 'onClose');
		spyOn(component.annonceChange, 'emit');
		spyOnProperty(component, 'isUpdating', 'get').and.returnValue(false);
		component.formAd.patchValue({
			title: 'A',
			description: 'B'
		});
		annonceServiceStub.createAd.and.returnValue(of({}));

		// WHEN
		component.onSave();

		// THEN
		expect(component.onClose).toHaveBeenCalled();
		expect(annonceServiceStub.createAd).toHaveBeenCalled();
		expect(component.annonceChange.emit).toHaveBeenCalled();
		expect(component.loading).toBeFalsy();
	});

	it('should tester save cas modification.', () => {
		// GIVEN
		spyOn(component, 'onClose');
		spyOn(component.annonceChange, 'emit');
		spyOnProperty(component, 'isUpdating', 'get').and.returnValue(true);
		component.formAd.patchValue({
			title: 'A',
			description: 'B'
		});
		annonceServiceStub.updateAd.and.returnValue(of({}));
		component.annonce = new Annonce();
		component.annonce.id = 1;

		// WHEN
		component.onSave();

		// THEN
		expect(component.onClose).toHaveBeenCalled();
		expect(annonceServiceStub.updateAd).toHaveBeenCalled();
		expect(component.annonceChange.emit).toHaveBeenCalled();
		expect(component.loading).toBeFalsy();
	});

	it('should tester on delete', () => {
		// WHEN
		component.delete();

		// THEN
		expect(component.url).toBeUndefined();
		expect(component.uploadedFile).toBeUndefined();
	});
});
