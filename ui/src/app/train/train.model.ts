export interface ServerEvent {
  type: string;
  value: TrainLocationEvent | null;
}

export interface TrainLocationEvent {
  id: string;
  name: string;
  speed: number;
  destination: string;
  coordinates: LonLat
}

interface LonLat {
  lon: number;
  lat: number;
}
