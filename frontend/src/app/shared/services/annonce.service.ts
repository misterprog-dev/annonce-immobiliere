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

	/**
	 * Get one ad.
	 * @param id id of ad
	 */
	readAd(id: number): Observable<Annonce> {
		return this.http.get<Annonce>(`${this.url}/${id}`).pipe(
				map(annonce => new Annonce().deserialize(annonce)));
	}

	/**
	 * Create a ad.
	 * @param annonce the ad.
	 * @param uploadedFile image of ad.
	 */
	createAd(annonce: Annonce, uploadedFile: File): Observable<void> {
		const formData = new FormData();
		formData.append('image', uploadedFile);

		const blobAnnonce= new Blob([JSON.stringify(annonce.serialize())], {
			type: 'application/json',
		});
		formData.append('annonce', blobAnnonce);

		return this.http.post<void>(this.url, formData);
	}

	updateAd(id: number, annonce: Annonce, uploadedFile: File): Observable<void> {
		const formData = new FormData();
		formData.append('image', uploadedFile);

		const blobAnnonce= new Blob([JSON.stringify(annonce.serialize())], {
			type: 'application/json',
		});
		formData.append('annonce', blobAnnonce);

		return this.http.put<void>(`${this.url}/${id}`, formData);
	}

	/**
	 * Delete the ad.
	 * @param id id of Ad.
	 */
	deleteAd(id: number): Observable<void> {
		return this.http.delete<void>(`${this.url}/${id}`,{});
	}
}
