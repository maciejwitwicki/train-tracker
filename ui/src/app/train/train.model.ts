export interface ServerEvent {
  type: string;
  value: TrainLocation | null;
}

export interface TrainLocation {
  id: string;
  name: string;
  speed: number;
  destination: string;
  coordinates: LonLat
}

export interface LonLat {
  lat: number;
  lon: number;
}

