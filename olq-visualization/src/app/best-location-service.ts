import { Http } from '@angular/http';
import { map } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable()
export class BestLocationService {

    private olqApi: string;

    constructor(private http: Http) {
        this.olqApi = 'http://localhost:8080';
      }

    findBestLocation() {
        const url = `${this.olqApi}/findBestLocation`;
        console.log(url);
        return this.http.get(url).pipe(map( response => {
          return response.json();
        }));
      }
}
