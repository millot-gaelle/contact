/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContactTestModule } from '../../../test.module';
import { TelephoneComponent } from '../../../../../../main/webapp/app/entities/telephone/telephone.component';
import { TelephoneService } from '../../../../../../main/webapp/app/entities/telephone/telephone.service';
import { Telephone } from '../../../../../../main/webapp/app/entities/telephone/telephone.model';

describe('Component Tests', () => {

    describe('Telephone Management Component', () => {
        let comp: TelephoneComponent;
        let fixture: ComponentFixture<TelephoneComponent>;
        let service: TelephoneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContactTestModule],
                declarations: [TelephoneComponent],
                providers: [
                    TelephoneService
                ]
            })
            .overrideTemplate(TelephoneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TelephoneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TelephoneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Telephone(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.telephones[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
