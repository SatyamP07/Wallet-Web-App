import { Router } from '@angular/router';
import { AccountService } from './../../service/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit } from '@angular/core';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
private customer: CustomerDetails;
private accountId: number;
private password: string;
private invalidFlag: boolean;
private customers: CustomerDetails[];
private accountExists: boolean = false;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this._accountService.getAccounts().subscribe((customers) => this.customers = customers);
    this.invalidFlag=false;
  }

  login() {
    for(let i=0; i < this.customers.length; i++) {
      if (this.accountId == this.customers[i].accountId) {
        this.accountExists = true;
        break;
       }
    }
      if (this.accountExists) {
        this._accountService.getAccount(this.accountId).subscribe((customer) => this.customer = customer);
      if ( this.password == this.customer.accountPassword) {
        setTimeout(() => {
          this._accountService.setCustomer(this.customer);
        this._router.navigate(['/show']);
        }, 4000);
      } else {
        this.invalidFlag = true;
      }
      } else {
        this.invalidFlag = true;
      }
    }
}
