import { Component, OnInit } from '@angular/core';
import { circle, icon, latLng, Map, marker, point, polygon, tileLayer } from 'leaflet';
import monotoneChainConvexHull from 'monotone-chain-convex-hull';
import { BestLocationService } from '../best-location-service';

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
      this.putBestLocationOnMap(map, data.bestLocation);
      this.putClientsOnMap(map, data.clients);
      this.putFacilitiesOnMap(map, data.facilities);
    });
  }

  calculateTotalScore(data): number {
    let totalScore = data.bestLocation.score;

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
        color: 'green',
        fillColor: 'lightgreen',
        fillOpacity: 0.5,
        radius: this.getRadius(candidate.score)
      });

      const candidateAnchor = point(13, 13);
      // const candidateAnchor = point(16, 16);

      const candidateIcon = icon({
        iconUrl: 'assets/images/marker.png',
        iconAnchor: candidateAnchor
      });

      // const candidateIcon = icon({
      //   iconUrl: 'assets/images/silver_medal.png',
      //   iconAnchor: candidateAnchor,
      //   iconSize: [33, 46]
      // });

      const candidateMarker = marker([candidate.latitude, candidate.longitude], {
        icon: candidateIcon,
      });

      candidateMarker.bindPopup(`Latitude: ${candidate.latitude}<br>Longitude: ${candidate.longitude}`);
      candidateMarker.addTo(map);
      oldCandidateMarker.addTo(map);
      candidate.color = 'green';
      this.drawAttractedArea(map, candidate);
    }
  }

  putBestLocationOnMap(map: Map, bestLocation) {
    const oldBestLocationMarker = circle([bestLocation.latitude, bestLocation.longitude], {
      color: 'blue',
      fillColor: 'lightblue',
      fillOpacity: 0.5,
      radius: this.getRadius(bestLocation.score),
    });

    const bestLocationAnchor = point(16, 16);

    const bestLocationIcon = icon({
      iconUrl: 'assets/images/gold_medal.png',
      iconAnchor: bestLocationAnchor,
      iconSize: [33, 46]
    });

    const bestLocationMarker = marker([bestLocation.latitude, bestLocation.longitude], {
      icon: bestLocationIcon,
    });

    bestLocationMarker.bindPopup(`Latitude: ${bestLocation.latitude}<br>Longitude: ${bestLocation.longitude}`);
    bestLocationMarker.addTo(map);
    oldBestLocationMarker.addTo(map);
    bestLocation.color = 'blue';
    this.drawAttractedArea(map, bestLocation);
  }

  drawAttractedArea(map: Map, place) {
    const coordinates = [];
    for (const client of place.attractedClients) {
      coordinates.push([client.latitude, client.longitude]);
    }

    coordinates.push([place.latitude, place.longitude]);
    const result = monotoneChainConvexHull(coordinates);

    const attractedArea = polygon(result, {
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

  putFacilitiesOnMap(map: Map, facilities) {
    for (const facility of facilities) {
      const oldFacilityMarker = circle([facility.latitude, facility.longitude], {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: this.getRadius(facility.score)
      });

      const facilityAcnhor = point(12, 14);
      // const facilityAcnhor = point(16, 16);

      const facilityIcon = icon({
        iconUrl: 'assets/images/factory.png',
        iconAnchor: facilityAcnhor
      });

      // const facilityIcon = icon({
      //   iconUrl: 'assets/images/bronze_medal.png',
      //   iconAnchor: facilityAcnhor,
      //   iconSize: [33, 46]
      // });

      const facilityMarker = marker([facility.latitude, facility.longitude], {
        icon: facilityIcon
      });

      facilityMarker.bindPopup(`Latitude: ${facility.latitude}<br>Longitude: ${facility.longitude}`);
      facilityMarker.addTo(map);
      oldFacilityMarker.addTo(map);
      facility.color = 'red';
      this.drawAttractedArea(map, facility);
    }
  }

  getRadius(score: number): number {
    return ((100 * score) / this.totalScore) * 10;
  }
}
