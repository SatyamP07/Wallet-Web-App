import { AccountService } from '../../service/account.service';
import { CustomerDetails } from '../../customer-details';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-accounts',
  templateUrl: './show-accounts.component.html',
  styleUrls: ['./show-accounts.component.css']
})
export class ShowAccountsComponent implements OnInit {
  private customers: CustomerDetails[];
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this._accountService.getAccounts().subscribe((customers) => {
      console.log(customers);
      this.customers = customers;
    }, (error) => {
      console.log(error);
    }
    );
  }

  showAccount(customer) {
    this._accountService.setCustomer(customer);
    this._router.navigate(['/show']);
  }

}
