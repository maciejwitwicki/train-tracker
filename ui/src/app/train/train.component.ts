import {Component, OnInit, ViewChild} from '@angular/core';
import {TrainService} from "./train.service";
import {LonLat, TrainLocation} from "./train.model";
import {GoogleMap, MapMarker} from "@angular/google-maps";
import {MapService} from "./map.service";

@Component({
  templateUrl: './train.component.html',
  styleUrls: ['./train.component.scss']
})
export class TrainComponent implements OnInit {

  @ViewChild('googleMap', {static: false}) map: GoogleMap | undefined;

  zoom = 12;
  center: google.maps.LatLngLiteral = {lat: 0, lng: 0};
  options: google.maps.MapOptions = {
    zoomControl: true,
    scrollwheel: true,
    disableDoubleClickZoom: true,
    maxZoom: 30,
    minZoom: 4,
  };

  markers: MapMarker[] = [];
  trains: Map<string, TrainLocation> = new Map<string, TrainLocation>();

  constructor(private trainService: TrainService, private mapService: MapService) {

  }

  ngOnInit(): void {
    this.trainService.getServerSentEvent()
      .subscribe({
        next: event => this.handleTrainEvent(event),
        error: this.handleError,
      })

    navigator.geolocation.getCurrentPosition((position) => {
      this.center = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      }
    })
  }

  zoomToFit() {
    const bounds = this.markers
      .reduce((acc, item) =>  acc.extend(item.position), new google.maps.LatLngBounds());

    this.map?.fitBounds(bounds);
  }

  private handleTrainEvent(trainLocation: TrainLocation) {
    console.log('got train event', trainLocation);
    console.log('map obj', this.map);

    const id = trainLocation.id;
    if (this.trains.get(id)) {
      this.trains.set(id, trainLocation);
      this.markers = Array.from(this.trains.values())
        .map(t => this.mapService.trainToMarker(t));
    } else {
      this.trains.set(id, trainLocation);
      this.markers.push(this.mapService.trainToNewMarker(trainLocation));
    }
  }

  trainLocations(): TrainLocation[] {
    return Array.from(this.trains.values());
  }

  addMarker() {
    const lat = this.center.lat + ((Math.random() - 0.5) * 2) / 10;
    const lon = this.center.lng + ((Math.random() - 0.5) * 2) / 10;

    const id = (Math.random() + 1).toString(36).substring(7);
    const loc: TrainLocation = {
      id,
      name: id + "-name",
      speed: Math.random(),
      destination: id + "-dest",
      coordinates: {lon: lon, lat: lat}
    }

    this.handleTrainEvent(loc);
  }

  private handleError(error: Error) {
    console.error(error);
  }
}
