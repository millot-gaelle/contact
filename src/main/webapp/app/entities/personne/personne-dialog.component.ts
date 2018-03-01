import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Personne } from './personne.model';
import { PersonnePopupService } from './personne-popup.service';
import { PersonneService } from './personne.service';

@Component({
    selector: 'jhi-personne-dialog',
    templateUrl: './personne-dialog.component.html'
})
export class PersonneDialogComponent implements OnInit {

    personne: Personne;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private personneService: PersonneService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personneService.update(this.personne));
        } else {
            this.subscribeToSaveResponse(
                this.personneService.create(this.personne));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Personne>>) {
        result.subscribe((res: HttpResponse<Personne>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Personne) {
        this.eventManager.broadcast({ name: 'personneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-personne-popup',
    template: ''
})
export class PersonnePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personnePopupService: PersonnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.personnePopupService
                    .open(PersonneDialogComponent as Component, params['id']);
            } else {
                this.personnePopupService
                    .open(PersonneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
