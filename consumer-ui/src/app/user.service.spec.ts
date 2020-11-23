import {TestBed} from '@angular/core/testing';

import {UserService} from './user.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(UserService);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('#getUserById', () => {
    it('returned Observable should match the right data', () => {
      const mockUser: User = {
        id: '01',
        fullName: 'Fahmi BEN SALAH',
        role: 'ADMIN',
        permissions: []
      };

      service.get("01")
        .subscribe(userData => {
          expect(userData.fullName).toEqual('Fahmi BEN SALAH');
          expect(userData.role).toEqual('ADMIN');
        });

      const req = httpTestingController.expectOne(
        'http://localhost:9090/users/01'
      );

      req.flush(mockUser);
    });
  });
});
