import { Component, OnInit } from '@angular/core';
import {latLng, tileLayer, icon, circle, polyline} from 'leaflet';
import {Map, point} from 'leaflet';
import {Http} from '@angular/http';
import {map as mapOperator} from 'rxjs/operators';

@Component({
  selector: 'app-map-visualization',
  templateUrl: './map-visualization.component.html',
  styleUrls: ['./map-visualization.component.css']
})
export class MapVisualizationComponent implements OnInit {

  private olqApi: string;

  // Define our base layers so we can reference them multiple times
  googleMaps = tileLayer('http://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
    maxZoom: 20,
    subdomains: ['mt0', 'mt1', 'mt2', 'mt3'],
    detectRetina: true
  });

   // Set the initial set of displayed layers (we could also use the leafletLayers input binding for this)
  options = {
    layers: [ this.googleMaps],
    zoom: 12,
    center: latLng([ -12.919949, -38.419847 ])
  };

  constructor(private http: Http) {
    this.olqApi = 'http://localhost:8080';
  }

  ngOnInit() {
  }

  findBestLocation() {
    const url = `${this.olqApi}/findBestLocation`;
    console.log(url);
    return this.http.get(url).pipe(mapOperator( response => {
      return response.json();
    }));
  }

  onMapReady(map: Map) {
    const result = this.findBestLocation();
    result.subscribe(data => {
      this.putCandidatesOnMap(map, data.candidates);
      this.putBestLocationOnMap(map, data.bestLocation);
      this.putClientsOnMap(map, data.clients);
      this.putFacilitiesOnMap(map, data.facilities);
    });
  }

  putCandidatesOnMap(map: Map, candidates) {
    for (const candidate of candidates) {
      const clientMarker = circle([candidate.latitude, candidate.longitude], {
        color: 'green',
        fillColor: 'lightgreen',
        fillOpacity: 0.5,
        radius: 5
      });

      clientMarker.addTo(map);
    }
  }

  putBestLocationOnMap(map: Map, bestLocation) {
    const bestLocationMarker = circle([bestLocation.latitude, bestLocation.longitude], {
      color: 'blue',
      fillColor: 'lightblue',
      fillOpacity: 0.5,
      radius: 5
    });

    bestLocationMarker.addTo(map);
  }

  putClientsOnMap(map: Map, clients) {
    for (const client of clients) {
      const clientMarker = circle([client.latitude, client.longitude], {
        color: 'black',
        fillColor: 'lightblack',
        fillOpacity: 0.5,
        radius: 5
      });

      clientMarker.addTo(map);
    }
  }

  putFacilitiesOnMap(map: Map, facilities) {
    for (const facility of facilities) {
      const clientMarker = circle([facility.latitude, facility.longitude], {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: 5
      });

      clientMarker.addTo(map);
    }
  }
}
