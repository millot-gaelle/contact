import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Telephone } from './telephone.model';
import { TelephonePopupService } from './telephone-popup.service';
import { TelephoneService } from './telephone.service';
import { Personne, PersonneService } from '../personne';

@Component({
    selector: 'jhi-telephone-dialog',
    templateUrl: './telephone-dialog.component.html'
})
export class TelephoneDialogComponent implements OnInit {

    telephone: Telephone;
    isSaving: boolean;

    personnes: Personne[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private telephoneService: TelephoneService,
        private personneService: PersonneService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personneService.query()
            .subscribe((res: HttpResponse<Personne[]>) => { this.personnes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.telephone.id !== undefined) {
            this.subscribeToSaveResponse(
                this.telephoneService.update(this.telephone));
        } else {
            this.subscribeToSaveResponse(
                this.telephoneService.create(this.telephone));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Telephone>>) {
        result.subscribe((res: HttpResponse<Telephone>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Telephone) {
        this.eventManager.broadcast({ name: 'telephoneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPersonneById(index: number, item: Personne) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-telephone-popup',
    template: ''
})
export class TelephonePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private telephonePopupService: TelephonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.telephonePopupService
                    .open(TelephoneDialogComponent as Component, params['id']);
            } else {
                this.telephonePopupService
                    .open(TelephoneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
