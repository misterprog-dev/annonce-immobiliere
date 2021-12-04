import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { AnnonceService } from 'src/app/shared/services/annonce.service';
import { MessageService } from 'primeng/api';
import { finalize } from 'rxjs/operators';

@Component({
	selector: 'delete-ad-modal',
	templateUrl: './delete-ad.modal.html',
	styleUrls: ['./delete-ad.modal.scss']
})
export class DeleteAdModal implements OnInit {
	@Input() visible: boolean = false;
	// @ts-ignore
	@Input() annonce: Annonce;
	@Output() visibleChange: EventEmitter<boolean> = new EventEmitter<boolean>();
	@Output() annonceChange: EventEmitter<void> = new EventEmitter<void>();
	loading: boolean = false;

	constructor(private annonceService: AnnonceService,
				private messageService: MessageService) {
	}

	ngOnInit(): void {
	}

	/**
	 * Open modal
	 */
	openModal(): void {
		this.visibleChange.emit(true);
	}

	/**
	 * Close Modal
	 */
	onClose(): void {
		this.visibleChange.emit(false);
		this.loading = false;
	}

	/**
	 * Permit to delete ad.
	 */
	onSupprimer(): void {
		this.loading = true;
		// @ts-ignore
		this.annonceService.deleteAd(this.annonce?.id).pipe(
				finalize(() => {
					this.loading = false;
				})
		).subscribe(() => {
			this.onClose();
			this.messageService.add({
				severity: 'success',
				summary: 'Deleting',
				detail: 'Ad deteled successfully'
			});
			this.annonceChange.emit();
		}, (error) => {
			this.messageService.add({
				severity: 'error',
				summary: 'Error deleting',
				detail: 'Error : ' + error.errors
			});
		});
	}
}
