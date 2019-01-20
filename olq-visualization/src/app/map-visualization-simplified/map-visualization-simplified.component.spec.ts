import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MapVisualizationSimplifiedComponent } from './map-visualization-simplified.component';

describe('MapVisualizationSimplifiedComponent', () => {
  let component: MapVisualizationSimplifiedComponent;
  let fixture: ComponentFixture<MapVisualizationSimplifiedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MapVisualizationSimplifiedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MapVisualizationSimplifiedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
