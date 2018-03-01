import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Telephone } from './telephone.model';
import { TelephonePopupService } from './telephone-popup.service';
import { TelephoneService } from './telephone.service';

@Component({
    selector: 'jhi-telephone-delete-dialog',
    templateUrl: './telephone-delete-dialog.component.html'
})
export class TelephoneDeleteDialogComponent {

    telephone: Telephone;

    constructor(
        private telephoneService: TelephoneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.telephoneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'telephoneListModification',
                content: 'Deleted an telephone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-telephone-delete-popup',
    template: ''
})
export class TelephoneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private telephonePopupService: TelephonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.telephonePopupService
                .open(TelephoneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
