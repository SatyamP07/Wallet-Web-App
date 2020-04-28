import { CustomerDetails } from './../../customer-details';
import { Router } from '@angular/router';
import { AccountService } from './../../services/account.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-access',
  templateUrl: './admin-access.component.html',
  styleUrls: ['./admin-access.component.css']
})
export class AdminAccessComponent implements OnInit {
  private customers: CustomerDetails[];
  private customer: CustomerDetails;
  private show: boolean;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this._accountService.getAccounts().subscribe((customers) => this.customers = customers);
  }

  showAccount(i) {
    this.customer = this.customers[i];
    this.show = true;
  }

  deleteAccount(i) {
    this._accountService.deleteAccount(this.customers[i].accountId).subscribe();
  }
}
