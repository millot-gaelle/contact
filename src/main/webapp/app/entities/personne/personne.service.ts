import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Personne } from './personne.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Personne>;

@Injectable()
export class PersonneService {

    private resourceUrl =  SERVER_API_URL + 'api/personnes';

    constructor(private http: HttpClient) { }

    create(personne: Personne): Observable<EntityResponseType> {
        const copy = this.convert(personne);
        return this.http.post<Personne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(personne: Personne): Observable<EntityResponseType> {
        const copy = this.convert(personne);
        return this.http.put<Personne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Personne>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Personne[]>> {
        const options = createRequestOption(req);
        return this.http.get<Personne[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Personne[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Personne = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Personne[]>): HttpResponse<Personne[]> {
        const jsonResponse: Personne[] = res.body;
        const body: Personne[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Personne.
     */
    private convertItemFromServer(personne: Personne): Personne {
        const copy: Personne = Object.assign({}, personne);
        return copy;
    }

    /**
     * Convert a Personne to a JSON which can be sent to the server.
     */
    private convert(personne: Personne): Personne {
        const copy: Personne = Object.assign({}, personne);
        return copy;
    }
}
