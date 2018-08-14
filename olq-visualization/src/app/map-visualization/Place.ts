import { Circle, Polygon, Polyline, LatLngExpression, Marker } from 'leaflet';
import { circle, polygon, polyline, marker, divIcon } from 'leaflet';
import monotoneChainConvexHull from 'monotone-chain-convex-hull';

export class Place {
    private attractedArea: Polygon;
    private colorMarker: string;
    private marker: Circle;
    private maxDistanceLocation: Polyline;
    private minDistanceLocation: Polyline;
    private info: Marker;
    private headerInfo: string;
    private infoLocation: Marker;
    private totalClients: number;
    private maxDistance: number;
    private minDistance: number;
    private avgDistance: number;

    constructor(private latitude: number, private longitude: number) {
    }

    private createMarker(): void {
        this.marker =  circle([this.latitude, this.longitude], {
            color: this.colorMarker,
            fillOpacity: 0.8,
            opacity: 1,
            radius: 250,
            weight: 1
          });
    }

    setAttractedClients(clients): void {
        const coordinates = [];
        this.totalClients = clients.length;
        for (const client of clients) {
          coordinates.push([client.latitude, client.longitude]);
        }

        coordinates.push([this.latitude, this.longitude]);
        const result = monotoneChainConvexHull(coordinates);

        this.attractedArea = polygon(result, {
          color: this.colorMarker,
          weight: 1
        });
    }

    setFarthestClient(client): void {
        this.maxDistanceLocation = this.getPolyline(client, 'yellow');
    }

    setClosestClient(client): void {
        this.minDistanceLocation = this.getPolyline(client, 'lightgreen');
    }

    private getPolyline(client, lineColor: string): Polyline {
        const points = [];
        points.push([client.latitude, client.longitude]);
        points.push(this.getCoordinates());

       return polyline(points, {
        weight: 2,
        color: lineColor
        });
    }

    getFarthestClient(): Polyline {
        return this.maxDistanceLocation;
    }

    getClosestClient(): Polyline {
        return this.minDistanceLocation;
    }

    setColorMarker(colorMarker: string) {
        this.colorMarker = colorMarker;
    }

   getAttractedArea(): Polygon {
       return this.attractedArea;
   }

   getMarker(): Circle {
       if (this.marker == null) {
           this.createMarker();
       }
       return this.marker;
   }

   getInfo(): Marker {
    if (this.info == null) {
        this.createInfo();
    }
    return this.info;
   }

   setMaxDistance(maxDistance: number): void {
       this.maxDistance = maxDistance;
   }

   setMinDistance(minDistance: number): void {
       this.minDistance = minDistance;
   }

   setAverageDistance(avgDistance: number): void {
       this.avgDistance = avgDistance;
   }

   private createInfo(): void {
    const div = divIcon({
        html: `<h4 style="webkit-margin-after: 0em;">${this.headerInfo} Info</h4>
               Latitude: ${this.latitude}<br/>
               Longitude: ${this.longitude}<br/>
               Total clients: ${this.totalClients}<br/>
               Min Distance: ${this.minDistance.toFixed(2)} km<br/>
               Max Distance: ${this.maxDistance.toFixed(2)} km<br/>
               Avg Distance: ${this.avgDistance.toFixed(2)} km`
      });
      this.info = this.infoLocation.setIcon(div);
   }

   setInfoLocation(infoLocation: Marker): void {
       this.infoLocation = infoLocation;
   }

   equals(other: Place): boolean {
       if (other == null) {
           return false;
        }
       return this.latitude === other.latitude && this.longitude === other.longitude;
   }

   getCoordinates(): LatLngExpression {
       return [this.latitude, this.longitude];
   }

   setHeaderInfo(header: string): void {
       this.headerInfo = header;
   }
}
