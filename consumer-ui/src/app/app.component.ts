import {Component} from '@angular/core';
import {UserService} from "./user.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  userId: string;
  user$: Observable<User>;

  constructor(private userService: UserService) {

  }

  displayUserData() {
    this.user$ = this.userService.get(this.userId);
  }

}
