import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private BASE_URL = '/users';

  constructor(private httpClient: HttpClient) {
  }

  get(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.BASE_URL}/${id}`);
  }

}
