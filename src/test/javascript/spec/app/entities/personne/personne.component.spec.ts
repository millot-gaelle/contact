/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContactTestModule } from '../../../test.module';
import { PersonneComponent } from '../../../../../../main/webapp/app/entities/personne/personne.component';
import { PersonneService } from '../../../../../../main/webapp/app/entities/personne/personne.service';
import { Personne } from '../../../../../../main/webapp/app/entities/personne/personne.model';

describe('Component Tests', () => {

    describe('Personne Management Component', () => {
        let comp: PersonneComponent;
        let fixture: ComponentFixture<PersonneComponent>;
        let service: PersonneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContactTestModule],
                declarations: [PersonneComponent],
                providers: [
                    PersonneService
                ]
            })
            .overrideTemplate(PersonneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Personne(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.personnes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
