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
  private facilities: Array<Place>;

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
  }

  ngOnInit() {
  }

  onMapReady(map: Map) {
    const result = this.bestLocationService.findBestLocation();
    result.subscribe(data => {
      this.totalScore = this.calculateTotalScore(data);
      console.log(this.totalScore);
      this.putCandidatesOnMap(map, data.candidates);
      this.putFirstBestLocationOnMap(map, data.firstBestLocation);
      this.putSecondBestLocationOnMap(map, data.secondBestLocation);
      this.putThirdBestLocationOnMap(map, data.thirdBestLocation);
      this.putClientsOnMap(map, data.clients);
      this.putFacilitiesOnMap(map, data.facilities);
    });
  }

  calculateTotalScore(data): number {
    let totalScore = data.firstBestLocation.score;
    totalScore += data.secondBestLocation.score;
    totalScore += data.thirdBestLocation.score;

    for (const facility of data.facilities) {
      totalScore += facility.score;
    }

    for (const candidate of data.candidates) {
      totalScore += candidate.score;
    }

    return totalScore;
  }

  putCandidatesOnMap(map: Map, candidates) {
    for (const candidate of candidates) {
      const oldCandidateMarker = circle([candidate.latitude, candidate.longitude], {
        color: 'gray',
        fillColor: 'lightgray',
        fillOpacity: 0.5,
        radius: this.getRadius(candidate.score)
      });
      oldCandidateMarker.addTo(map);

      const place = new Place(candidate.latitude, candidate.longitude);
      place.setIconUrl('assets/images/marker.png');
      place.setIconAnchor(point(13, 17));
      place.setAttractedClients(candidate.attractedClients);
      this.drawPlace(map, place);
    }
  }

  putFirstBestLocationOnMap(map: Map, bestLocation) {
    const oldBestLocationMarker = circle([bestLocation.latitude, bestLocation.longitude], {
      color: 'blue',
      fillColor: 'lightblue',
      fillOpacity: 0.5,
      radius: this.getRadius(bestLocation.score),
    });
    oldBestLocationMarker.addTo(map);

    const place = new Place(bestLocation.latitude, bestLocation.longitude);
    place.setIconUrl('assets/images/gold_medal.png');
    place.setIconAnchor(point(16, 16));
    place.setIconSize([33, 46]);
    place.setAttractedClients(bestLocation.attractedClients);
    this.drawPlace(map, place);
  }

  putSecondBestLocationOnMap(map: Map, secondBestLocation) {
    const oldBestLocationMarker = circle([secondBestLocation.latitude, secondBestLocation.longitude], {
      color: 'blue',
      fillColor: 'lightblue',
      fillOpacity: 0.5,
      radius: this.getRadius(secondBestLocation.score),
    });
    oldBestLocationMarker.addTo(map);

    const place = new Place(secondBestLocation.latitude, secondBestLocation.longitude);
    place.setIconUrl('assets/images/silver_medal.png');
    place.setIconAnchor(point(16, 16));
    place.setIconSize([33, 46]);
    place.setAttractedClients(secondBestLocation.attractedClients);
    this.drawPlace(map, place);
  }

  putThirdBestLocationOnMap(map: Map, thirdBestLocation) {
    const oldBestLocationMarker = circle([thirdBestLocation.latitude, thirdBestLocation.longitude], {
      color: 'blue',
      fillColor: 'lightblue',
      fillOpacity: 0.5,
      radius: this.getRadius(thirdBestLocation.score),
    });
    oldBestLocationMarker.addTo(map);

    const place = new Place(thirdBestLocation.latitude, thirdBestLocation.longitude);
    place.setIconUrl('assets/images/bronze_medal.png');
    place.setIconAnchor(point(16, 16));
    place.setIconSize([33, 46]);
    place.setAttractedClients(thirdBestLocation.attractedClients);
    this.drawPlace(map, place);
  }

  drawPlace(map: Map, place: Place) {
    const markerPlace = place.getMarker();
    markerPlace.addTo(map);
    markerPlace.on('click', event => {
      if (this.lastPlaceClicked != null) {
        map.removeLayer(this.lastPlaceClicked.getAttractedArea());
      }
      place.getAttractedArea().addTo(map);
      this.lastPlaceClicked = place;
      this.updateFacilities(map);
    });
  }

  updateFacilities(map: Map) {
    for (const facility of this.facilities) {
      map.removeLayer(facility.getAttractedArea());
      facility.getAttractedArea().addTo(map);
    }
  }

  drawAttractedArea(map: Map, place) {
    const coordinates = [];
    for (const client of place.attractedClients) {
      coordinates.push([client.latitude, client.longitude]);
    }

    coordinates.push([place.latitude, place.longitude]);
    const result = monotoneChainConvexHull(coordinates);

    polygon(result, {
      color: place.color,
      weight: 0
    }).addTo(map);
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

  putFacilitiesOnMap(map: Map, facilitiesJson) {
    this.facilities = new Array<Place>();
    for (const facility of facilitiesJson) {
      const oldFacilityMarker = circle([facility.latitude, facility.longitude], {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: this.getRadius(facility.score)
      });
      oldFacilityMarker.addTo(map);

      const place = new Place(facility.latitude, facility.longitude);
      place.setIconUrl('assets/images/factory32.png');
      place.setIconAnchor(point(16, 18));
      place.setColorArea('red');

      const facilityMarker = place.getMarker();
      facilityMarker.addTo(map);
      place.setAttractedClients(facility.attractedClients);

      this.facilities.push(place);
    }
  }

  getRadius(score: number): number {
    return ((100 * score) / this.totalScore) * 10;
  }
}
