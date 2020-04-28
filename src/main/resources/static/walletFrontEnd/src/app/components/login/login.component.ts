import { Router } from '@angular/router';
import { AccountService } from './../../services/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private customer: CustomerDetails;
  private accountId: number;
  private password: string  = null;
  private invalidFlag: boolean;
  private customers: CustomerDetails[];
  private accountExists: boolean = false;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this._accountService.getAccounts().subscribe((customers) => {
      this.customers = customers;
      console.log(customers);
    });
    this.customer = {
      'accountId': undefined,
      'accountPassword': undefined,
      'name': undefined,
      'eMail': undefined,
      'mobileNumber': undefined,
      'transactionPin': undefined,
      'balance': undefined,
      'nationalIdType': undefined,
      'nationalIdNumber': undefined,
      'transactionDetails': []
    };
    this.invalidFlag=false;
  }

  login(loginForm) {
    for(let i=0; i < this.customers.length; i++) {
      if (this.accountId == this.customers[i].accountId) {
        console.log(this.customers[i].accountId);
        this.accountExists = true;
        break;
       }
    }
      if (this.accountExists) {
        this.accountExists = false;
        this._accountService.getAccount(this.accountId).subscribe((customer) => this.customer = customer);
        setTimeout(() => {
          console.log(this.customer);
          if ( this.password == this.customer.accountPassword) {
            setTimeout(() => {
              console.log(this.customer);
              this._accountService.setCustomer(this.customer);
              this._accountService.setIsSignedIn(true);
            this._router.navigate(['/show/account']);
            }, 1500);
          } else {
            this.invalidFlag = true;
          }
        },1500);
      } else {
        this.invalidFlag = true;
      }
    }

}
