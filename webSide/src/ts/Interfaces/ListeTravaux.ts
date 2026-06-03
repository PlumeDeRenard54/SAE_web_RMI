export type ListeTravaux = Array<ZoneTravaux>

export type ZoneTravaux = {
  type: string
  description: string
  short_description: string
  starttime: string
  endtime: string
  location: {
    street: string
    polyline: string
    location_description: string
  }
  source: {
    name: string
    reference: string
  }
  updatetime: string
  creationtime: string
  id: string
}