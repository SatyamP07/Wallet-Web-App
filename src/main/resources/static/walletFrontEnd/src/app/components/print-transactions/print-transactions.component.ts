import { CustomerDetails } from './../../customer-details';
import { Router } from '@angular/router';
import { AccountService } from './../../services/account.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-print-transactions',
  templateUrl: './print-transactions.component.html',
  styleUrls: ['./print-transactions.component.css']
})
export class PrintTransactionsComponent implements OnInit {
  private customer: CustomerDetails;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this.customer = this._accountService.getCustomer();
  }

}
