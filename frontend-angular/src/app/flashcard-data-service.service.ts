import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpRequest } from '@angular/common/http';
import { FlashcardData } from './data-model/flashcard-data';
import { Category } from './data-model/category';
import { DataFormatType } from './data-model/data-format-type';
import { Observable } from 'rxjs/internal/Observable';
@Injectable({
  providedIn: 'root'
})
export class FlashcardDataServiceService {

  constructor(private http: HttpClient) { }
  readonly ROOT_URL = 'http://localhost:8000/api';

  getFlashcard(){
    let headers = new HttpHeaders();
    headers.append('Accept', 'application/json');
    const requestOptions = { headers: headers};
    return this.http.get<FlashcardData>(this.ROOT_URL + '/getFlashcard', requestOptions);

  }

  getImage(id: number) {
    return this.http.get(`${this.ROOT_URL}/getImage/${id}`, { responseType: 'blob' });
  }

  addFlashcard(flashcard: FlashcardData): Observable<FlashcardData> {
    return this.http.post<FlashcardData>(this.ROOT_URL + '/addFlashcard', flashcard);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.ROOT_URL + '/getCategories');
  }

  getDataFormatTypes(): Observable<DataFormatType[]> {
    return this.http.get<DataFormatType[]>(this.ROOT_URL + '/getDataFormatTypes');
  }

}
