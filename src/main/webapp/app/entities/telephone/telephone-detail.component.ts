import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Telephone } from './telephone.model';
import { TelephoneService } from './telephone.service';

@Component({
    selector: 'jhi-telephone-detail',
    templateUrl: './telephone-detail.component.html'
})
export class TelephoneDetailComponent implements OnInit, OnDestroy {

    telephone: Telephone;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private telephoneService: TelephoneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTelephones();
    }

    load(id) {
        this.telephoneService.find(id)
            .subscribe((telephoneResponse: HttpResponse<Telephone>) => {
                this.telephone = telephoneResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTelephones() {
        this.eventSubscriber = this.eventManager.subscribe(
            'telephoneListModification',
            (response) => this.load(this.telephone.id)
        );
    }
}
