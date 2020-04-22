import { AccountService } from './../../services/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private customer: CustomerDetails;
  private isSignedIn: boolean;
  private logIn: boolean;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this.customer = this._accountService.getCustomer();
    this.logIn = false;
    this.isSignedIn = this._accountService.getIsSignedIn();
  }
  newAccount() {
    const customer = new CustomerDetails;
    this._accountService.setCustomer(customer);
    this._accountService.setIsSignedIn(false);
    console.log(this._accountService.getIsSignedIn());
    this._router.navigate(['/form']);
  }
  login() {
    this.logIn = true;
  }

  showAccount() {
    this._accountService.setCustomer(this.customer);
    this._router.navigate(['/show']);
  }

}
