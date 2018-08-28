import { Http, Headers } from '@angular/http';
import { HttpHeaders } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Place } from './map-visualization/Place';

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
        console.log(response.statusText);
        return response.json();
      }));
    }

    inputCandidates(inputCandidates: Set<Place>) {
      const url = `${this.olqApi}/input-candidates`;
      console.log(url);
      const json = this.convertToJson(inputCandidates);

      this.http.post(url, json).toPromise().then(
        response => {
          console.log(`Status: ${response.status}, StatusText: ${response.statusText}`);
          console.log(`Headers: ${response.headers.toJSON}`);
        },
        message => console.error(`Error: ${message.statusText}`)
      );
    }

    private convertToJson(inputCandidates: Set<Place>): any {
      const json = {candidates: []};
      inputCandidates.forEach(candidate => {
        json.candidates.push({latitude: candidate.getLatitude(), longitude: candidate.getLongitude()});
      });
      console.log(json);
      return json;
    }
}
