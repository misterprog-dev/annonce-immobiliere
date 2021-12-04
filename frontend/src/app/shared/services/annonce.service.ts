import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Annonce } from 'src/app/shared/models/annonce.model';
import { map } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class AnnonceService {

	private url = 'http://localhost:9080/api/annonces';

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

	/**
	 * Update a ad.
	 *
	 * @param id the id of ad.
	 * @param annonce the ad.
	 * @param uploadedFile image of ad.
	 */
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

	/**
	 * Load a file
	 *
	 * @param folder the folder of file.
	 * @param fileName file name
	 * @return file
	 */
	getFile(folder: string, fileName: string): Observable<HttpResponse<Blob>> {
		const httpOptions = {
			responseType: 'arraybuffer' as 'json',
			observe: 'response' as 'body'
		};
		return this.http.get<HttpResponse<Blob>>(`${this.url}/${folder}/${fileName}/file`, httpOptions);
	}
}
