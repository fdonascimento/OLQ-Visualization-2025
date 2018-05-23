import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MapVisualizationComponent } from './map-visualization.component';

describe('MapVisualizationComponent', () => {
  let component: MapVisualizationComponent;
  let fixture: ComponentFixture<MapVisualizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MapVisualizationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MapVisualizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
