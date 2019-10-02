import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBranchOffice } from 'app/shared/model/branch-office.model';

type EntityResponseType = HttpResponse<IBranchOffice>;
type EntityArrayResponseType = HttpResponse<IBranchOffice[]>;

@Injectable({ providedIn: 'root' })
export class BranchOfficeService {
  public resourceUrl = SERVER_API_URL + 'api/branch-offices';

  constructor(protected http: HttpClient) {}

  create(branchOffice: IBranchOffice): Observable<EntityResponseType> {
    return this.http.post<IBranchOffice>(this.resourceUrl, branchOffice, { observe: 'response' });
  }

  update(branchOffice: IBranchOffice): Observable<EntityResponseType> {
    return this.http.put<IBranchOffice>(this.resourceUrl, branchOffice, { observe: 'response' });
  }

  find(code: string, number: string): Observable<EntityResponseType> {
    return this.http.get<IBranchOffice>(`${this.resourceUrl}/${code}/${number}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBranchOffice[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
