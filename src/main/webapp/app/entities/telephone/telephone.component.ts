import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Telephone } from './telephone.model';
import { TelephoneService } from './telephone.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-telephone',
    templateUrl: './telephone.component.html'
})
export class TelephoneComponent implements OnInit, OnDestroy {
telephones: Telephone[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private telephoneService: TelephoneService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.telephoneService.query().subscribe(
            (res: HttpResponse<Telephone[]>) => {
                this.telephones = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTelephones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Telephone) {
        return item.id;
    }
    registerChangeInTelephones() {
        this.eventSubscriber = this.eventManager.subscribe('telephoneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
