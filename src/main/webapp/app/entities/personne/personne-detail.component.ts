import { Adresse } from '../adresse/adresse.model';
import { AdresseService } from '../adresse/adresse.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Personne } from './personne.model';
import { PersonneService } from './personne.service';

@Component({
    selector: 'jhi-personne-detail',
    templateUrl: './personne-detail.component.html'
})
export class PersonneDetailComponent implements OnInit, OnDestroy {

    personne: Personne;
    private adresseList: Adresse[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private personneService: PersonneService,
        private adresseService: AdresseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonnes();
    }

    load(id) {
        this.personneService.find(id)
            .subscribe((personneResponse: HttpResponse<Personne>) => {
                this.personne = personneResponse.body;
                this.adresseService.findByPersonne(id).subscribe((adresseResponse: HttpResponse<Adresse[]>) => {
                    this.adresseList = adresseResponse.body;
                  });
                });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonnes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personneListModification',
            (response) => this.load(this.personne.id)
        );
    }
}
