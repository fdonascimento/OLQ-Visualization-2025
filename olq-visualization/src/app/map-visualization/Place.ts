import { Icon, Circle, circle, marker, Marker, point, polygon, Polygon, Point, PointExpression } from 'leaflet';
import monotoneChainConvexHull from 'monotone-chain-convex-hull';

export class Place {
    private attractedArea: Polygon;
    private colorArea: string;
    private colorMarker: string;
    private marker: Circle;

    constructor(private latitude: number, private longitude: number) {
        this.colorArea = 'blue';
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

    setColorArea(color: string) {
        this.colorArea = color;
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

   equals(other: Place): boolean {
       if (other == null) {
           return false;
        }
       return this.latitude === other.latitude && this.longitude === other.longitude;
   }
}
