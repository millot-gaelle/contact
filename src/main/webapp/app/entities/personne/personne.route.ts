import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PersonneComponent } from './personne.component';
import { PersonneDetailComponent } from './personne-detail.component';
import { PersonnePopupComponent } from './personne-dialog.component';
import { PersonneDeletePopupComponent } from './personne-delete-dialog.component';

@Injectable()
export class PersonneResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const personneRoute: Routes = [
    {
        path: 'personne',
        component: PersonneComponent,
        resolve: {
            'pagingParams': PersonneResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'personne/:id',
        component: PersonneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personnePopupRoute: Routes = [
    {
        path: 'personne-new',
        component: PersonnePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personne/:id/edit',
        component: PersonnePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personne/:id/delete',
        component: PersonneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
