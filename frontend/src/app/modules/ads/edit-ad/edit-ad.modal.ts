import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileUpload } from 'primeng/fileupload';
import { MessageService } from 'primeng/api';
import { RequiredValidators } from 'src/app/shared/validators/required.validator';
import { AnnonceService } from 'src/app/shared/services/annonce.service';
import { finalize } from 'rxjs/operators';

@Component({
	selector: 'edit-ad-modal',
	templateUrl: './edit-ad.modal.html',
	styleUrls: ['./edit-ad.modal.scss']
})
export class EditAdModal implements OnInit {
	@Input() visible: boolean = false;
	// @ts-ignore
	@Input() annonce: Annonce;
	@Output() visibleChange: EventEmitter<boolean> = new EventEmitter<boolean>();
	@Output() annonceChange: EventEmitter<void> = new EventEmitter<void>();
	// @ts-ignore
	@ViewChild('fileUpload') fileUpload: FileUpload;
	loading: boolean = false;
	// @ts-ignore
	formAd: FormGroup;
	uploadedFile: any;

	constructor(private formBuilder: FormBuilder,
				private annonceService: AnnonceService,
				private messageService: MessageService) {
	}

	ngOnInit(): void {
		this.formAd = this.formBuilder.group({
			id: [{ value: null, disabled: true }],
			title: [null, [Validators.required, RequiredValidators.notBlank]],
			description: [null, [Validators.required, RequiredValidators.notBlank]]
		});
	}

	/**
	 * Open modal
	 */
	openModalEdition(): void {
		this.visibleChange.emit(true);
		if (this.isUpdating) {
			this.activateUpdating();
		}
	}

	/**
	 * Read data for updating.
	 */
	activateUpdating(): void {
		// @ts-ignore
		this.annonceService.readAd(this.annonce?.id).subscribe((annonce) => {
			this.formAd.patchValue(annonce);
		}, (error => {
			this.messageService.add({
				severity: 'error',
				summary: 'Error ad reading',
				detail: 'Error : ' + error.errors
			});
		}));
	}

	/**
	 * Close Modal
	 */
	onClose(): void {
		this.visibleChange.emit(false);
		this.formAd.reset();
		this.loading = false;
	}

	/**
	 * Check if ad exists
	 * @return true if ad existant, false else.
	 */
	get isUpdating(): boolean {
		return !!this.annonce;
	}

	/**
	 * Permit to get modal mode title.
	 */
	getEditingModalTitle(): string {
		return !this.annonce ? 'Création d\'une annonce' : 'Modification d\'une annonce';
	}

	/**
	 * Save a ad.
	 */
	onSave() {
		this.loading = true;
		let annonceModified = Object.assign(new Annonce(), this.formAd.getRawValue());
		// @ts-ignore
		const saving$ = !this.isUpdating ? this.annonceService.createAd(annonceModified, this.uploadedFile) : this.annonceService.updateAd(this.annonce.id, annonceModified, this.uploadedFile);
		saving$.pipe(
				finalize(() => {
					this.loading = false;
				})
		).subscribe(() => {
			this.onClose();
			this.messageService.add({
				severity: 'success',
				summary: 'Saving',
				detail: 'Ad saved successfully'
			});
			this.annonceChange.emit();
		}, (error) => {
			this.messageService.add({
				severity: 'error',
				summary: 'Error saving',
				detail: 'Error : ' + error.errors
			});
		});
	}

	/**
	 * Permit to upload image.
	 *
	 * @param $event the event that permit to select image.
	 */
	onUpload($event: any) {
		this.uploadedFile = $event.files[0];
		this.fileUpload.clear();
	}

	/**
	 * Permit to show message on image selected.
	 *
	 * @param $event the event that permit to select image.
	 * @param fileUpload composant on modal for uploading.
	 */
	validateFile($event: any, fileUpload: FileUpload) {
		if (fileUpload.msgs.length > 0) {
			this.messageService.add({
				severity: 'error',
				summary: 'Error Image',
				detail: 'Error : ' + fileUpload.msgs[0].detail
			});
		}
	}
}
