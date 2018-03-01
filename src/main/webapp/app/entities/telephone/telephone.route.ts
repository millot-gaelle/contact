import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TelephoneComponent } from './telephone.component';
import { TelephoneDetailComponent } from './telephone-detail.component';
import { TelephonePopupComponent } from './telephone-dialog.component';
import { TelephoneDeletePopupComponent } from './telephone-delete-dialog.component';

export const telephoneRoute: Routes = [
    {
        path: 'telephone',
        component: TelephoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Telephones'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'telephone/:id',
        component: TelephoneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Telephones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const telephonePopupRoute: Routes = [
    {
        path: 'telephone-new',
        component: TelephonePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Telephones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'telephone/:id/edit',
        component: TelephonePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Telephones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'telephone/:id/delete',
        component: TelephoneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Telephones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
