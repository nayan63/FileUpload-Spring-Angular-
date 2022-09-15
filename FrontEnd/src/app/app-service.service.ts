import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  constructor(private http:HttpClient) { }

  base_url = 'http://localhost:9090/';

  uploadFile(file:any): Observable<any>
  {
    return this.http.post<any>(this.base_url+'upload-file', file);
  }

  getFile(file:string): Observable<Blob>
  {
    return this.http.get(this.base_url+'get/'+file, {responseType:"blob"});
  }
}