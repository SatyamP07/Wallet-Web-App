import { Router } from '@angular/router';
import { AccountService } from './../../service/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit, ElementRef } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private customer: CustomerDetails;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this.customer = this._accountService.getCustomer();
  }
  newAccount() {
    const customer = new CustomerDetails;
    this._accountService.setCustomer(customer);
    this._accountService.setCreateForm(true);
    this._router.navigate(['/form']);
  }
  login() {
    this._router.navigate(['/login'])
  }

}
