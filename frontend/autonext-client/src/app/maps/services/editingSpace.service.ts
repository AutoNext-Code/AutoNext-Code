import { inject, Injectable } from '@angular/core';
import { PlugType } from '@maps/enums/plugType.enum';
import { Observable } from 'rxjs';
import { EditingHttpSpaceService } from './editingSpace-http.service';
import { HttpParams } from '@angular/common/http';
import { JobPosition } from '@admin/enums/jobPosition.enum';

@Injectable({
  providedIn: 'root',
})
export class EditingSpaceService {

  private editingHttpSpaceService: EditingHttpSpaceService = inject(EditingHttpSpaceService) ;

  spaceEdit(id: number, plugType: string, jobPosition: JobPosition): Observable<string> {

    const params = new HttpParams()
      .set('id', id.toString())
      .set('plugType', plugType)
      .set('jobPosition', JobPosition[jobPosition]);

    return this.editingHttpSpaceService.spaceEdit(params) ;
  }

}
