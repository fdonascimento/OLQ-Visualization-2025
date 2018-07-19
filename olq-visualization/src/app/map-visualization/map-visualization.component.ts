import { Component, OnInit } from '@angular/core';
import { circle, latLng, Map, polyline, tileLayer } from 'leaflet';
import { BestLocationService } from '../best-location-service';
import { Place } from './Place';

@Component({
  selector: 'app-map-visualization',
  templateUrl: './map-visualization.component.html',
  styleUrls: ['./map-visualization.component.css'],
  providers: [BestLocationService]
})
export class MapVisualizationComponent implements OnInit {

  private googleMaps;
  public options;
  private totalScore: number;
  private lastPlaceClicked: Place;
  private candidates: Array<Place>;
  private facilities: Array<Place>;
  private map: Map;

  constructor(private bestLocationService: BestLocationService) {
    // Define our base layers so we can reference them multiple times
    this.googleMaps = tileLayer('http://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
      maxZoom: 20,
      subdomains: ['mt0', 'mt1', 'mt2', 'mt3'],
      detectRetina: true
    });

    // Set the initial set of displayed layers (we could also use the leafletLayers input binding for this)
    this.options = {
      layers: [ this.googleMaps],
      zoom: 12,
      center: latLng([ -12.919949, -38.419847 ])
    };

    this.candidates = new Array<Place>();
  }

  ngOnInit() {
  }

  onMapReady(map: Map) {
    this.map = map;
    const result = this.bestLocationService.findBestLocation();
    result.subscribe(data => {
      this.totalScore = this.calculateTotalScore(data);
      console.log(this.totalScore);
      this.putClientsOnMap(map, data.clients);
      this.putFacilitiesOnMap(map, data.facilities);
      this.putCandidatesOnMap(map, data.candidates);
      this.putBestLocationOnMap(map, data.firstBestLocation);
      this.drawLine();
    });
  }

  private calculateTotalScore(data): number {
    let totalScore = data.firstBestLocation.score;

    for (const candidate of data.candidates) {
      totalScore += candidate.score;
    }

    return totalScore;
  }

  private putCandidatesOnMap(map: Map, candidates) {
    for (const candidate of candidates) {
      const place = new Place(candidate.latitude, candidate.longitude);
      place.setColorMarker('blue');
      place.setAttractedClients(candidate.attractedClients);
      this.candidates.push(place);
      this.drawPlace(map, place);
    }
  }

  private putBestLocationOnMap(map: Map, bestLocation) {
    const place = new Place(bestLocation.latitude, bestLocation.longitude);
    place.setColorMarker('purple');
    place.setAttractedClients(bestLocation.attractedClients);
    this.candidates.push(place);
    this.drawPlace(map, place);
  }

  private drawPlace(map: Map, place: Place) {
    const markerPlace = place.getMarker();
    markerPlace.addTo(map);
    markerPlace.on('click', event => this.seeAttractedArea(place, markerPlace));
  }

  private seeAttractedArea(place: Place, markerPlace: any) {
    if (this.lastPlaceClicked != null) {
      this.map.removeLayer(this.lastPlaceClicked.getAttractedArea());
      this.map.removeLayer(markerPlace);
      this.lastPlaceClicked = null;
      this.showCandidates();
    } else {
      this.hideCandidates();
      place.getAttractedArea().addTo(this.map);
      markerPlace.addTo(this.map);
      this.lastPlaceClicked = place;
    }
  }

  private seeInfo(place: Place, markerPlace: any) {

  }
  private hideCandidates() {
    for (const candidate of this.candidates) {
      this.map.removeLayer(candidate.getMarker());
    }
  }

  private showCandidates() {
    for (const candidate of this.candidates) {
      candidate.getMarker().addTo(this.map);
    }
  }

  private putClientsOnMap(map: Map, clients) {

    for (const client of clients) {
      const clientMarker = circle([client.latitude, client.longitude], {
        color: 'green',
        fillOpacity: 0.5,
        opacity: 0.5,
        radius: 80,
        weight: 0
      });

      clientMarker.addTo(map);
    }
  }

  private putFacilitiesOnMap(map: Map, facilitiesJson) {
    this.facilities = new Array<Place>();
    for (const facility of facilitiesJson) {
      const place = new Place(facility.latitude, facility.longitude);
      place.setColorMarker('red');
      place.setAttractedClients(facility.attractedClients);

      place.getAttractedArea().addTo(map);
      place.getMarker().addTo(map);
      this.facilities.push(place);
    }
  }

  private getRadius(score: number): number {
    return ((100 * score) / this.totalScore) * 10;
  }

  public hideFacilitiesAttractedArea() {
    for (const facility of this.facilities) {
      this.map.removeLayer(facility.getAttractedArea());
    }
  }

  public showFacilitiesAttractedArea() {
    for (const facility of this.facilities) {
      facility.getAttractedArea().addTo(this.map);
      facility.getAttractedArea().bringToBack();
    }
  }

  drawLine() {
    const points = [];
    points.push(this.candidates[0].getCoordinates());
    points.push(this.candidates[1].getCoordinates());

    const line = polyline(points, {
      weight: 2,
      color: 'yellow'
    }).addTo(this.map);
  }
}
