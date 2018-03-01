import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContactSharedModule } from '../../shared';
import {
    PersonneService,
    PersonnePopupService,
    PersonneComponent,
    PersonneDetailComponent,
    PersonneDialogComponent,
    PersonnePopupComponent,
    PersonneDeletePopupComponent,
    PersonneDeleteDialogComponent,
    personneRoute,
    personnePopupRoute,
    PersonneResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...personneRoute,
    ...personnePopupRoute,
];

@NgModule({
    imports: [
        ContactSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PersonneComponent,
        PersonneDetailComponent,
        PersonneDialogComponent,
        PersonneDeleteDialogComponent,
        PersonnePopupComponent,
        PersonneDeletePopupComponent,
    ],
    entryComponents: [
        PersonneComponent,
        PersonneDialogComponent,
        PersonnePopupComponent,
        PersonneDeleteDialogComponent,
        PersonneDeletePopupComponent,
    ],
    providers: [
        PersonneService,
        PersonnePopupService,
        PersonneResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContactPersonneModule {}
