import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContactSharedModule } from '../../shared';
import {
    TelephoneService,
    TelephonePopupService,
    TelephoneComponent,
    TelephoneDetailComponent,
    TelephoneDialogComponent,
    TelephonePopupComponent,
    TelephoneDeletePopupComponent,
    TelephoneDeleteDialogComponent,
    telephoneRoute,
    telephonePopupRoute,
} from './';

const ENTITY_STATES = [
    ...telephoneRoute,
    ...telephonePopupRoute,
];

@NgModule({
    imports: [
        ContactSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TelephoneComponent,
        TelephoneDetailComponent,
        TelephoneDialogComponent,
        TelephoneDeleteDialogComponent,
        TelephonePopupComponent,
        TelephoneDeletePopupComponent,
    ],
    entryComponents: [
        TelephoneComponent,
        TelephoneDialogComponent,
        TelephonePopupComponent,
        TelephoneDeleteDialogComponent,
        TelephoneDeletePopupComponent,
    ],
    providers: [
        TelephoneService,
        TelephonePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContactTelephoneModule {}
