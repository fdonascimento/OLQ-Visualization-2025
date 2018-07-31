import { Component, OnInit } from '@angular/core';
import { circle, latLng, Map, marker, tileLayer, divIcon, Marker } from 'leaflet';
import { BestLocationService } from '../best-location-service';
import { Place } from './Place';
import { Console } from '@angular/core/src/console';

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
      this.putClientsOnMap(map, data.clients);
      this.putFacilitiesOnMap(map, data.facilities);
      this.putCandidatesOnMap(data.candidates);
      this.putBestLocationOnMap(data.firstBestLocation);
      this.map.on('click', event => {
        // const latlng = map.mouseEventToLatLng(event.originalEvent);
        // console.log(`latitude: ${latlng.lat}, longitude: ${latlng.lng}`);
      });
    });
  }

  private putCandidatesOnMap(candidates) {
    for (const candidate of candidates) {
      this.putCandidateOnMap(candidate, 'blue');
    }
  }

  private putBestLocationOnMap(bestLocation) {
    this.putCandidateOnMap(bestLocation, 'purple');
  }

  private putCandidateOnMap(candidate, color: string): void {
    const place = new Place(candidate.latitude, candidate.longitude);
    place.setHeaderInfo('Candidate');
    place.setMinDistance(candidate.minDistance);
    place.setMaxDistance(candidate.maxDistance);
    place.setAverageDistance(candidate.averageDistance);
    place.setInfoLocation(marker([-12.976122149086684, -38.2485580444336]));
    place.setColorMarker(color);
    place.setAttractedClients(candidate.attractedClients);
    place.setFarthestClient(candidate.farthestClient);
    place.setClosestClient(candidate.closestClient);
    this.candidates.push(place);
    this.drawPlace(this.map, place);
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
    this.map.removeLayer(candidate.getMaxRay());
    this.map.removeLayer(candidate.getMinRay());
  }

  private showCandidateLayers(place: Place): void {
    place.getAttractedArea().addTo(this.map);
    place.getAttractedArea().bringToBack();
    // place.getMaxRay().addTo(this.map);
    place.getMarker().addTo(this.map);
    // place.getMinRay().addTo(this.map);
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
      // facility.getMinRay().addTo(this.map);
      // facility.getMaxRay().addTo(this.map);
      this.drawInfo(facility.getInfo());
    }
  }

  private hideFacilityInfo(facility: Place): void {
    if (facility != null) {
      this.map.removeLayer(facility.getFarthestClient());
      this.map.removeLayer(facility.getClosestClient());
      this.map.removeLayer(facility.getInfo());
      this.map.removeLayer(facility.getMaxRay());
      this.map.removeLayer(facility.getMinRay());
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
