import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { map } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class AnnonceService {

	private url = '/api/annonces';

	constructor(private http: HttpClient) {
	}

	/**
	 * Get all ads.
	 */
	readAds(): Observable<Annonce[]> {
		return this.http.get<Annonce[]>(this.url).pipe(
				map(annonces => annonces.map(annonce => new Annonce().deserialize(annonce)))
		);
	}
}
