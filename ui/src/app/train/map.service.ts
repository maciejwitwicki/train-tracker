import {Injectable} from "@angular/core";
import {TrainLocation} from "./train.model";
import {MapMarker} from "@angular/google-maps";

@Injectable()
export class MapService {

  trainToNewMarker(train: TrainLocation) {
    return this.mapTrainToMarker(train, google.maps.Animation.DROP);
  }

  trainToMarker(train: TrainLocation) {
    return this.mapTrainToMarker(train, null);
  }

  private mapTrainToMarker(train: TrainLocation, animation: google.maps.Animation | null): MapMarker {
    const svgMarker = {
      path: "M17.2 20l1.8 1.5v.5H5v-.5L6.8 20H5a2 2 0 0 1-2-2V7a4 4 0 0 1 4-4h10a4 4 0 0 1 4 4v11a2 2 0 0 1-2 2h-1.8zM5 7v4h14V7H5zm7 11a2 2 0 1 0 0-4 2 2 0 0 0 0 4z",
      fillColor: "blue",
      fillOpacity: 0.9,
      strokeWeight: 0,
      rotation: 0,
      scale: 2,
      anchor: new google.maps.Point(0, 20),
    };
    return {
      position: {
        lat: train.coordinates.lat,
        lng: train.coordinates.lon
      },
      label: {
        color: 'red',
        text: `[${train.id}] ${train.name} to ${train.destination}`,
      },
      title: `[${train.id}] ${train.name} to ${train.destination} with speed ${train.speed} Km/h`,
      options: {
        icon: svgMarker,
        animation: animation,
      },
    } as MapMarker;
  }

}
