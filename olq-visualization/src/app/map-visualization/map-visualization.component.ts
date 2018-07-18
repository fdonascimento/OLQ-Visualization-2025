import { Component, OnInit } from '@angular/core';
import { circle, icon, latLng, Map, marker, point, polygon, tileLayer } from 'leaflet';
import monotoneChainConvexHull from 'monotone-chain-convex-hull';
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
    const result = this.bestLocationService.findBestLocation();
    result.subscribe(data => {
      this.totalScore = this.calculateTotalScore(data);
      console.log(this.totalScore);
      this.putClientsOnMap(map, data.clients);
      this.putFacilitiesOnMap(map, data.facilities);
      this.putCandidatesOnMap(map, data.candidates);
      this.putBestLocationOnMap(map, data.firstBestLocation);
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
    markerPlace.on('click', event => {
      if (this.lastPlaceClicked != null) {
        map.removeLayer(this.lastPlaceClicked.getAttractedArea());
        map.removeLayer(markerPlace);
        this.lastPlaceClicked = null;
        this.showCandidates(map);
      } else {
        this.hideCandidates(map);
        place.getAttractedArea().addTo(map);
        markerPlace.addTo(map);
        this.lastPlaceClicked = place;
      }
    });
  }

  private hideCandidates(map: Map) {
    for (const candidate of this.candidates) {
      map.removeLayer(candidate.getMarker());
    }
  }

  private showCandidates(map: Map) {
    for (const candidate of this.candidates) {
      candidate.getMarker().addTo(map);
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
    for (const facility of facilitiesJson) {
      const place = new Place(facility.latitude, facility.longitude);
      place.setColorMarker('red');
      place.setAttractedClients(facility.attractedClients);

      place.getAttractedArea().addTo(map);
      place.getMarker().addTo(map);
    }
  }

  private getRadius(score: number): number {
    return ((100 * score) / this.totalScore) * 10;
  }
}
