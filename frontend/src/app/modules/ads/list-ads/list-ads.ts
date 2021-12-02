import { Component, OnInit, ViewChild } from '@angular/core';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { AnnonceService } from 'src/app/shared/services/annonce.service';
import { finalize } from 'rxjs/operators';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';

@Component({
	selector: 'app-list-ads',
	templateUrl: './list-ads.html',
	styleUrls: ['./list-ads.scss']
})
export class ListAds implements OnInit {
	@ViewChild('annonceDG') annonceDG: Table | undefined;
	loading: boolean = false;
	annonces: Annonce[] = [];
	annonceSelectionned: Annonce = new Annonce();

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
}
