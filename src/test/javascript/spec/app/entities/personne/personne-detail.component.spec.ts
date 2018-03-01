/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ContactTestModule } from '../../../test.module';
import { PersonneDetailComponent } from '../../../../../../main/webapp/app/entities/personne/personne-detail.component';
import { PersonneService } from '../../../../../../main/webapp/app/entities/personne/personne.service';
import { Personne } from '../../../../../../main/webapp/app/entities/personne/personne.model';

describe('Component Tests', () => {

    describe('Personne Management Detail Component', () => {
        let comp: PersonneDetailComponent;
        let fixture: ComponentFixture<PersonneDetailComponent>;
        let service: PersonneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContactTestModule],
                declarations: [PersonneDetailComponent],
                providers: [
                    PersonneService
                ]
            })
            .overrideTemplate(PersonneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Personne(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.personne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
