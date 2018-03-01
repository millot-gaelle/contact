import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ContactPersonneModule } from './personne/personne.module';
import { ContactAdresseModule } from './adresse/adresse.module';
import { ContactTelephoneModule } from './telephone/telephone.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ContactPersonneModule,
        ContactAdresseModule,
        ContactTelephoneModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContactEntityModule {}
