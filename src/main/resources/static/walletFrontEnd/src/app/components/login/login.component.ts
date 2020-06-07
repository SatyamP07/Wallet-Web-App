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
  private accountExists: boolean;
  private adminLogin: boolean;
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
    if (this.adminLogin) {
      if (this.password == 'QWERTY123!') {
        this.invalidFlag = false;
        this._router.navigate(['/admin']);
      }
      else {
        this.invalidFlag = true;
      }
    } else {
      this._accountService.getAccount(this.accountId).subscribe((customer) => {this.customer = customer;
        this.accountExists = true;
      }, err => {
        this.accountExists = false;
      }
      );
      setTimeout(() =>
      {
        if (this.accountExists) {
          this._accountService.checkAccountPassword(this.password, this.accountId)
            .subscribe(flag => this.invalidFlag = false, err => {this.invalidFlag = true; console.log(this.invalidFlag)});
            setTimeout(() => {
              if (!this.invalidFlag) {
                this._accountService.setCustomer(this.customer);
                this._accountService.setIsSignedIn(true);
              this._router.navigate(['/show/account']);
              }
            }, 1000);
        } else {
          this.invalidFlag = true;
        }
      }, 1000);
    }
  }

  accessFlag() {
    this.invalidFlag = false;
    this.adminLogin = !this.adminLogin;
  }

}
