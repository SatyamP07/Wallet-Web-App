import { CustomerDetails } from './../../customer-details';
import { Router } from '@angular/router';
import { AccountService } from './../../services/account.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {
  private customer: CustomerDetails;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this.customer = this._accountService.getCustomer();
  }

}
