/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ContactTestModule } from '../../../test.module';
import { PersonneDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/personne/personne-delete-dialog.component';
import { PersonneService } from '../../../../../../main/webapp/app/entities/personne/personne.service';

describe('Component Tests', () => {

    describe('Personne Management Delete Component', () => {
        let comp: PersonneDeleteDialogComponent;
        let fixture: ComponentFixture<PersonneDeleteDialogComponent>;
        let service: PersonneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContactTestModule],
                declarations: [PersonneDeleteDialogComponent],
                providers: [
                    PersonneService
                ]
            })
            .overrideTemplate(PersonneDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonneDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
