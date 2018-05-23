import { Component, OnInit } from '@angular/core';
import {latLng, tileLayer, icon, marker, polyline} from 'leaflet';
import {Map, point} from 'leaflet';
import {Http} from '@angular/http';
import {map} from 'rxjs/operators';

class BestLocation {
  constructor(private latitude: string, private longitude: string) {

  }
}

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

  // Marker for the top of Mt. Ranier
  summit = marker([46.8523, -121.7603], {
    icon: icon({
      iconSize: [25, 41],
      iconAnchor: [13, 41],
      iconUrl: 'leaflet/marker-icon.png',
      shadowUrl: 'leaflet/marker-shadow.png'
    })
  });

   // Set the initial set of displayed layers (we could also use the leafletLayers input binding for this)
  options = {
    layers: [ this.googleMaps, this.summit ],
    zoom: 12,
    center: latLng([ -12.919949, -38.419847 ])
  };

  constructor(private http: Http) {
    this.olqApi = 'http://localhost:8080';
  }

  ngOnInit() {
    this.findBestLocation();
  }

  findBestLocation() {
    const url = `${this.olqApi}/bestLocation`;
    console.log(url);
    this.http.get(url).subscribe(res => {
      const bestLocation = res.json();
      this.summit.setLatLng([bestLocation.latitude, bestLocation.longitude]);
      console.log(bestLocation);
    });
  }

}
