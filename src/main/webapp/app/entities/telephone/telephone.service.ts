import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Telephone } from './telephone.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Telephone>;

@Injectable()
export class TelephoneService {

    private resourceUrl =  SERVER_API_URL + 'api/telephones';

    constructor(private http: HttpClient) { }

    create(telephone: Telephone): Observable<EntityResponseType> {
        const copy = this.convert(telephone);
        return this.http.post<Telephone>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(telephone: Telephone): Observable<EntityResponseType> {
        const copy = this.convert(telephone);
        return this.http.put<Telephone>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Telephone>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Telephone[]>> {
        const options = createRequestOption(req);
        return this.http.get<Telephone[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Telephone[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Telephone = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Telephone[]>): HttpResponse<Telephone[]> {
        const jsonResponse: Telephone[] = res.body;
        const body: Telephone[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Telephone.
     */
    private convertItemFromServer(telephone: Telephone): Telephone {
        const copy: Telephone = Object.assign({}, telephone);
        return copy;
    }

    /**
     * Convert a Telephone to a JSON which can be sent to the server.
     */
    private convert(telephone: Telephone): Telephone {
        const copy: Telephone = Object.assign({}, telephone);
        return copy;
    }
}
