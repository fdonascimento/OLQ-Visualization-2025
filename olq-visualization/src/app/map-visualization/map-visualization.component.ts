import { Component, OnInit } from '@angular/core';
import { circle, DomEvent, latLng, Map, marker, Marker, tileLayer } from 'leaflet';
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
  private lastCandidateClicked: Place;
  private lastFacilityClicked: Place;
  private candidates: Array<Place>;
  private facilities: Array<Place>;
  private map: Map;
  private inputCandidatesSet: Set<Place>;
  private inputLatitudes: Set<any>;
  private inputLongitudes: Set<any>;
  private lastInfluenceCandidate: Place;

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
    this.inputCandidatesSet = new Set<Place>();
    this.inputLatitudes = new Set<any>();
    this.inputLongitudes = new Set<any>();
  }

  ngOnInit() {
  }

  onMapReady(map: Map) {
    this.map = map;
    const result = this.bestLocationService.findBestLocation();
    result.subscribe(data => {
      this.putClientsOnMap(map, data.clients);
      this.putFacilitiesOnMap(map, data.facilities);
      // this.putCandidatesOnMap(data.candidates);
      // this.putBestLocationOnMap(data.firstBestLocation);
      this.map.on('click', event => {
        // const latlng = map.mouseEventToLatLng(event.originalEvent);
        // console.log(`latitude: ${latlng.lat}, longitude: ${latlng.lng}`);
      });
    });
  }

  findBestLocation() {
    this.map.off('click');
    this.candidates.forEach(candidate => {
      this.inputCandidatesSet.add(candidate);
      this.map.removeLayer(candidate.getMarker());
    });
    this.bestLocationService.inputCandidates(this.inputCandidatesSet).subscribe(() => {
      this.clearInputCandidates();
      const result = this.bestLocationService.findBestLocation();
      result.subscribe(data => {
        if (data.candidates.length > 0) {
          this.putCandidatesOnMap(data.candidates);
          this.putBestLocationOnMap(data.firstBestLocation);
        }
      });
    });
  }

  private clearInputCandidates(): void {
    this.inputCandidatesSet.forEach(candidate => {
      this.map.removeLayer(candidate.getMarker());
    });
    this.inputCandidatesSet.clear();
    this.inputLatitudes.clear();
    this.inputLongitudes.clear();
  }

  clearAllCandidates(): void {
    this.candidates.forEach(candidate => {
      this.map.removeLayer(candidate.getMarker());
    });
    this.candidates = [];
    this.clearInputCandidates();
  }

  showInfluenceArea(): void {
    this.map.off('click');
    this.map.on('click', (event: any) => {
      const latlng = event.latlng;

      const result = this.bestLocationService.getInfluenceArea(latlng.lat, latlng.lng);
      result.subscribe(data => {
          this.disableInfluenceArea();
          this.lastInfluenceCandidate = this.putCandidateOnMap(data.candidates[0], 'blue');
          this.showCandidateLayers(this.lastInfluenceCandidate);
      });
    });
  }

  disableInfluenceArea() {
    if (this.lastInfluenceCandidate != null) {
      this.map.removeLayer(this.lastInfluenceCandidate.getMarker());
      this.map.removeLayer(this.lastInfluenceCandidate.getAttractedArea());
      this.removeCandidateInfo(this.lastInfluenceCandidate);
      this.lastInfluenceCandidate = null;
    }
  }

  inputCandidates() {
    this.disableInfluenceArea();
    this.map.off('click');
    this.map.on('click', (event: any) => {
      const latlng = event.latlng;
      this.inputCandidate(latlng.lat, latlng.lng);
    });
  }

  inputCandidate(latitude: number, longitude: number) {
    if (!this.inputLatitudes.has(latitude) && !this.inputLongitudes.has(longitude)) {
      const candidate = new Place(latitude, longitude);
      candidate.setColorMarker('blue');
      const markerCandidate = candidate.getMarker();
      markerCandidate.addTo(this.map);
      markerCandidate.on('click', (event: any) => {
        this.inputLatitudes.delete(latitude);
        this.inputLongitudes.delete(longitude);
        this.map.removeLayer(markerCandidate);
        DomEvent.stopPropagation(event);
      });

      this.inputLatitudes.add(latitude);
      this.inputLongitudes.add(longitude);
      this.inputCandidatesSet.add(candidate);
    }
  }

  markAsCandidate() {
    this.inputCandidate(this.lastInfluenceCandidate.getLatitude(), this.lastInfluenceCandidate.getLongitude());
    this.map.removeLayer(this.lastInfluenceCandidate.getAttractedArea());
    this.removeCandidateInfo(this.lastInfluenceCandidate);
    this.lastInfluenceCandidate = null;
  }

  disableInputCandidates() {
    this.map.off('click');
  }

  private putCandidatesOnMap(candidates) {
    for (const candidate of candidates) {
      this.putCandidateOnMap(candidate, 'blue');
    }
  }

  private putBestLocationOnMap(bestLocation) {
    this.putCandidateOnMap(bestLocation, 'purple');
  }

  private putCandidateOnMap(candidate, color: string): Place {
    const place = new Place(candidate.latitude, candidate.longitude);
    place.setHeaderInfo('Candidate');
    place.setMinDistance(candidate.minDistance);
    place.setMaxDistance(candidate.maxDistance);
    place.setAverageDistance(candidate.averageDistance);
    place.setColorMarker(color);
    place.setInfoLocation(marker([-12.976122149086684, -38.2485580444336]));

    place.setAttractedClients(candidate.attractedClients);
    place.setFarthestClient(candidate.farthestClient);
    place.setClosestClient(candidate.closestClient);
    this.candidates.push(place);
    this.drawPlace(this.map, place);
    return place;
  }

  private drawPlace(map: Map, place: Place) {
    const markerPlace = place.getMarker();
    markerPlace.addTo(map);
    markerPlace.on('click', () => this.seeAttractedArea(place));
  }

  private seeAttractedArea(place: Place) {
    if (this.lastCandidateClicked != null) {
      this.removeCandidateLayers(place);
      this.lastCandidateClicked = null;
      this.showCandidates();
    } else {
      this.hideCandidates();
      this.showCandidateLayers(place);
      this.lastCandidateClicked = place;
    }
  }

  private removeCandidateLayers(candidate: Place): void {
    this.map.removeLayer(this.lastCandidateClicked.getAttractedArea());
    this.map.removeLayer(candidate.getMarker());
    this.removeCandidateInfo(candidate);
  }

  private removeCandidateInfo(candidate: Place): void {
    this.map.removeLayer(candidate.getFarthestClient());
    this.map.removeLayer(candidate.getClosestClient());
    this.map.removeLayer(candidate.getInfo());
  }

  private showCandidateLayers(place: Place): void {
    place.getAttractedArea().addTo(this.map);
    place.getAttractedArea().bringToBack();
    place.getMarker().addTo(this.map);
    place.getFarthestClient().addTo(this.map);
    place.getClosestClient().addTo(this.map);
    this.drawInfo(place.getInfo());
  }

  private drawInfo(info: Marker): void {
    info.addTo(this.map);
    info.getElement().style.width = '200px';
    info.getElement().classList.remove('leaflet-div-icon');
    info.getElement().getElementsByTagName('h4').item(0).style.marginBottom = '0';
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
      place.setHeaderInfo('Facility');
      place.setMaxDistance(facility.maxDistance);
      place.setMinDistance(facility.minDistance);
      place.setAverageDistance(facility.averageDistance);
      place.setInfoLocation(marker([-12.91622950073752, -38.2485580444336]));
      place.setColorMarker('red');
      place.setAttractedClients(facility.attractedClients);
      place.setFarthestClient(facility.farthestClient);
      place.setClosestClient(facility.closestClient);

      place.getAttractedArea().addTo(map);
      place.getMarker().addTo(map);

      place.getMarker().on('click', () => this.showFacilityInfo(place));

      this.facilities.push(place);
    }
  }

  private showFacilityInfo(facility: Place) {
    if (this.lastFacilityClicked != null && this.lastFacilityClicked.equals(facility)) {
      this.hideFacilityInfo(facility);
      this.lastFacilityClicked = null;
    } else {
      this.hideFacilityInfo(this.lastFacilityClicked);
      this.lastFacilityClicked = facility;
      facility.getFarthestClient().addTo(this.map);
      facility.getClosestClient().addTo(this.map);
      this.drawInfo(facility.getInfo());
    }
  }

  private hideFacilityInfo(facility: Place): void {
    if (facility != null) {
      this.map.removeLayer(facility.getFarthestClient());
      this.map.removeLayer(facility.getClosestClient());
      this.map.removeLayer(facility.getInfo());
    }
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
      facility.getMarker();
    }
  }
}
