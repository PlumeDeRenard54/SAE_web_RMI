export type ListeVlib = Array<StationVlib>;

export type StationVlib = {
  station_id: string;
  name: string;
  lat: number;
  lon: number;
  address: string;
  capacity: number;
  num_bikes_available: number;
  vehicle_types_available: Array<{
    vehicle_type_id: string;
    count: number;
  }>;
  num_bikes_disabled: number;
  num_docks_available: number;
  num_docks_disabled: number;
  is_installed: boolean;
  is_renting: boolean;
  is_returning: boolean;
  last_reported: number;
  rental_methods?: Array<string>;
};
