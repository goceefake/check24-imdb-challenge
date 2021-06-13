import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

export class MoviesDto {
  constructor (public movieName: string) {}
}

export class UserDto {
  constructor (public username: string,
    public password: string) {}
}

@Injectable({
  providedIn: 'root'
})
export class WelcomeDataService {

  constructor(private http: HttpClient) { }

  executeHelloWorldBean(name) {
    let basicAuthHeaderString = this.createBasicHttpHeader();
    let headers = new HttpHeaders({
      Authorization: basicAuthHeaderString
    });

    // headers.append('Access-Control-Allow-Origin', 'http://localhost:4200');

    // return this.http.get(`http://localhost:8080/hello/${name}`, {headers: headers});

    return this.http.get<UserDto>(`http://localhost:8080/users/${name}`, 
    {headers});
  }

  createBasicHttpHeader() {
    let username='cem';
    let password='pass';
    let basicAuthHeader = 'Basic ' + window.btoa(username + ':' + password);
    return basicAuthHeader;
  }

}
