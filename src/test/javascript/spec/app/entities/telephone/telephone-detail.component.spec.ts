/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ContactTestModule } from '../../../test.module';
import { TelephoneDetailComponent } from '../../../../../../main/webapp/app/entities/telephone/telephone-detail.component';
import { TelephoneService } from '../../../../../../main/webapp/app/entities/telephone/telephone.service';
import { Telephone } from '../../../../../../main/webapp/app/entities/telephone/telephone.model';

describe('Component Tests', () => {

    describe('Telephone Management Detail Component', () => {
        let comp: TelephoneDetailComponent;
        let fixture: ComponentFixture<TelephoneDetailComponent>;
        let service: TelephoneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContactTestModule],
                declarations: [TelephoneDetailComponent],
                providers: [
                    TelephoneService
                ]
            })
            .overrideTemplate(TelephoneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TelephoneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TelephoneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Telephone(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.telephone).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
