import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UPDATE_SPACE_PLUG_JOB } from '../../config';

@Injectable({
  providedIn: 'root',
})
export class EditingHttpSpaceService {
  private http: HttpClient = inject(HttpClient);

  spaceEdit(params: HttpParams): Observable<string> {
    
    console.log('→ Params toString:', params.toString());

    return this.http.put(
      UPDATE_SPACE_PLUG_JOB,
      null,                   // cuerpo vacío
      {
        params,
        responseType: 'text'  // <— ahora TypeScript sabe que es texto
      }
    );
  }
}
