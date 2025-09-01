import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PartDTO } from '../interfaces/PartDTO';

@Injectable({
  providedIn: 'root'
})
export class PartService {

  private url = 'http://localhost:8081/parts';

  constructor(private http: HttpClient) { }
  public getParts(): Observable<PartDTO[]> {
    return this.http.get<PartDTO[]>(`${this.url}/all`);
  }

  public getPartsByIsActive(isActive: boolean): Observable<PartDTO[]> {
    return this.http.get<PartDTO[]>(`${this.url}/active/${isActive}`);
  }
  public savePart(part: PartDTO): Observable<PartDTO> {
    return this.http.post<PartDTO>(`${this.url}/createPart`, part);
  }
  /**
   * The parameters addStock is boolean than indicates if the stock is added or subtracted
   * @param part
   * @param addStock
   * @returns
   */
  public updatePart(part: PartDTO, addStock: boolean): Observable<PartDTO> {
    return this.http.put<PartDTO>(`${this.url}/updatePart/${addStock}`, part);
  }

  public findPartByNameAndSupplier(namePart: string, supplierId: number): Observable<PartDTO> {
    return this.http.get<PartDTO>(
      `${this.url}/findByNamePartAndSupplierId/${namePart}/${supplierId}`
    );
  }
}
