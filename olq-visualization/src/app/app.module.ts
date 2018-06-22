import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {LeafletModule} from '@asymmetrik/ngx-leaflet';
import { HttpModule } from '@angular/http';
import { MapVisualizationComponent } from './map-visualization/map-visualization.component';

@NgModule({
  declarations: [
    AppComponent,
    MapVisualizationComponent
  ],
  imports: [
    BrowserModule,
    LeafletModule.forRoot(),
    HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
