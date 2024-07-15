import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  private baseUrl = 'http://localhost:8899/api/orders';

  constructor(private http: HttpClient) {}

  getAllOrders(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/all`);
  }

  getSuccessOrders(searchCriteria: any) {
    return this.http.post<any[]>(`${this.baseUrl}/success`, searchCriteria);
  }

  getFailedOrders(searchCriteria: any) {
    return this.http.post<any[]>(`${this.baseUrl}/failed`, searchCriteria);
  }
}
