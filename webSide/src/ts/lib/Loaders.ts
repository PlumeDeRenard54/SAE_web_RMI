import { serverHost } from "../env";
import { ListeRestos } from '../Interfaces/ListeRestos';
import { ListeTravaux } from "../Interfaces/ListeTravaux";
import { ListeVlib } from "../Interfaces/ListeVlib";

export class Loaders {
  static instance: Loaders;

  restos?: ListeRestos;
  travaux?: ListeTravaux;
  velib?: ListeVlib;

  public static getInstance() {
    if (!this.instance) {
      this.instance = new Loaders();
    }

    return this.instance;
  }

  public async getRestos() {
    if (!this.restos){
        this.restos = await this.getData<ListeRestos>(serverHost + "/getRestos");
    }

    return this.restos;
  }

public async getTravaux() {
    if (!this.travaux){
        this.travaux = await this.getData<ListeTravaux>(serverHost + "/travaux");
    }

    return this.travaux;
  }

    public async getVelib() {
    if (!this.velib){
        this.velib = await this.getData<ListeVlib>(serverHost + "/velib");
    }

    return this.velib;
  }

  private async getData<T>(url: string) {
    try{
    return (await (await fetch(url)).json()) as T;
    }catch(e){
        throw new Error("API Not Found" + e)
    }
  }
}
