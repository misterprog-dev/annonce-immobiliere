import { Component, OnInit, ViewChild } from '@angular/core';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { AnnonceService } from 'src/app/shared/services/annonce.service';
import { finalize } from 'rxjs/operators';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
// @ts-ignore
import * as _ from 'lodash';

@Component({
	selector: 'app-list-ads',
	templateUrl: './list-ads.html',
	styleUrls: ['./list-ads.scss']
})
export class ListAds implements OnInit {
	// @ts-ignore
	@ViewChild('annonceDG') annonceDG: Table;
	loading: boolean = false;
	annonces: Annonce[] = [];
	// @ts-ignore
	annoncesSelectionnes: Annonce[] = [];
	// @ts-ignore
	annonceSelectionne: Annonce;
	editingVisible: boolean = false;
	deleteVisible: boolean = false;

	constructor(private annonceService: AnnonceService, private messageService: MessageService) {
	}

	ngOnInit(): void {
		this.readAds();
	}

	/**
	 * Read all ads.
	 */
	readAds(): void {
		this.loading = true;
		this.annonceService.readAds().pipe(
				finalize(() => {
					this.loading = false;
				})
		).subscribe((annonces) => {
			this.annonces = annonces;
		}, (err) => {
			this.messageService.add({
				severity: 'error',
				summary: 'Error ads reading',
				detail: 'Error : ' + err.errors
			});
		});
	}

	/**
	 * Open editing modal.
	 *
	 * @param annonce the ad to edit.
	 */
	openEditingModal(annonce?: Annonce): void {
		this.editingVisible = true;
		// @ts-ignore
		this.annonceSelectionne = annonce;
	}

	/**
	 * Open deleting modal.
	 *
	 * @param annonce the ad to delete.
	 */
	openDeletingModal(annonce: Annonce) {
		this.deleteVisible = true;
		// @ts-ignore
		this.annonceSelectionne = annonce ? annonce : null;
	}

	/**
	 * Callback to update list after operation.
	 */
	onAdsChanged(): void {
		this.readAds();
		// @ts-ignore
		this.annonceSelectionne = null;
		this.annoncesSelectionnes = [];
	}

	/**
	 * Method that permit to unchecked no existing item in table.
	 *
	 * @param filteredValues values in filters.
	 */
	onFilterTable(filteredValues: Annonce[]): void {
		this.annoncesSelectionnes = this.annoncesSelectionnes?.filter(value => _.find(filteredValues, value));
	}
}
