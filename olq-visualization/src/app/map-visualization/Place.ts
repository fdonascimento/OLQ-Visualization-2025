import { Circle, circle, polygon, Polygon, polyline, Polyline } from 'leaflet';
import monotoneChainConvexHull from 'monotone-chain-convex-hull';

export class Place {
    private attractedArea: Polygon;
    private colorMarker: string;
    private marker: Circle;
    private maxDistance: Polyline;
    private minDistance: Polyline;
    private clicked: boolean;

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

    setMaxDistance(client): void {
        this.maxDistance = this.getPolyline(client, 'yellow');
    }

    setMinDistance(client): void {
        this.minDistance = this.getPolyline(client, 'lightgreen');
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

    getMaxDistance(): Polyline {
        return this.maxDistance;
    }

    getMinDistance(): Polyline {
        return this.minDistance;
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

   getCoordinates() {
       return [this.latitude, this.longitude];
   }

   isClicked(): boolean {
       return this.clicked;
   }

   click(): void {
       this.clicked = !this.clicked;
   }

   setClicked(clicked: boolean): void {
       this.clicked = clicked;
   }
}
