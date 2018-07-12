import { Icon, icon, marker, Marker, point, polygon, Polygon, Point, PointExpression } from 'leaflet';
import monotoneChainConvexHull from 'monotone-chain-convex-hull';

export class Place {
    private icon: Icon;
    private attractedArea: Polygon;
    private colorArea;
    private marker: Marker;
    private iconUrl: string;
    private iconAnchor: Point;
    private iconSize: PointExpression;

    constructor(private latitude: number, private longitude: number) {
        this.colorArea = 'blue';
    }

    private createMarker(): void {
        this.icon = icon({
            iconUrl: this.iconUrl,
            iconAnchor: this.iconAnchor,
            iconSize: this.iconSize
        });

        this.marker = marker([this.latitude, this.longitude], {
            icon: this.icon,
        });

        this.marker.bindTooltip(`Latitude: ${this.latitude}<br>Longitude: ${this.longitude}`);
    }

    setAttractedClients(clients): void {
        const coordinates = [];
        for (const client of clients) {
          coordinates.push([client.latitude, client.longitude]);
        }

        coordinates.push([this.latitude, this.longitude]);
        const result = monotoneChainConvexHull(coordinates);

        this.attractedArea = polygon(result, {
          color: this.colorArea,
          weight: 0
        });
    }

    setIconUrl(iconUrl: string): void {
        this.iconUrl = iconUrl;
    }

    setIconAnchor(iconAnchor: Point): void {
        this.iconAnchor = iconAnchor;
    }

    setIconSize(iconSize: PointExpression): void {
        this.iconSize = iconSize;
    }

    setColorArea(color: string) {
        this.colorArea = color;
    }

   getAttractedArea(): Polygon {
       return this.attractedArea;
   }

   getMarker(): Marker {
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
