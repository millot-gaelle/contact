/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ContactTestModule } from '../../../test.module';
import { TelephoneDialogComponent } from '../../../../../../main/webapp/app/entities/telephone/telephone-dialog.component';
import { TelephoneService } from '../../../../../../main/webapp/app/entities/telephone/telephone.service';
import { Telephone } from '../../../../../../main/webapp/app/entities/telephone/telephone.model';
import { PersonneService } from '../../../../../../main/webapp/app/entities/personne';

describe('Component Tests', () => {

    describe('Telephone Management Dialog Component', () => {
        let comp: TelephoneDialogComponent;
        let fixture: ComponentFixture<TelephoneDialogComponent>;
        let service: TelephoneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContactTestModule],
                declarations: [TelephoneDialogComponent],
                providers: [
                    PersonneService,
                    TelephoneService
                ]
            })
            .overrideTemplate(TelephoneDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TelephoneDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TelephoneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Telephone(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.telephone = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'telephoneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Telephone();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.telephone = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'telephoneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
