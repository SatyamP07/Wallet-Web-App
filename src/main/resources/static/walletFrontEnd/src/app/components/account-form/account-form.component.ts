import { Router } from '@angular/router';
import { AccountService } from './../../services/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.css']
})
export class AccountFormComponent implements OnInit {
  private customer: CustomerDetails;
  private passwords: string[] = ['', '', ''];
  private formCount: number;
  private transactionPins: string[] = ['', '', ''];
  private isSignedIn: boolean;
  private editType: number;
  private passwordsNotMatched: boolean;
  private wrongPassword: boolean;
  private nationality: number = 0;

  constructor(private _accountService: AccountService, private _router: Router) {
    this._router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };
  }

  ngOnInit() {
    this.isSignedIn = this._accountService.getIsSignedIn();
    console.log(this.isSignedIn);
    if (this.isSignedIn) {
      this.customer = this._accountService.getCustomer();
    } else {
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
    }
    this.editType = this._accountService.getEditType();
    this.formCount = 0;
  }

  setDetails(detailsForm) {
    this.customer.name = detailsForm.value.name;
    this.customer.eMail = detailsForm.value.email;
    this.customer.mobileNumber = detailsForm.value.mobileNumber;
    this.customer.nationalIdType = detailsForm.value.nationalityType;
    this.customer.nationalIdNumber = detailsForm.value.nationalityNumber;
    console.log(detailsForm.value.nationalityType);
    console.log(detailsForm.value.nationalityNumber);
    this.formCount = 1;
  }
  setPassword(passwordForm) {
    this.passwords[0] = passwordForm.value.password1;
    this.passwords[1] = passwordForm.value.password2;
    if (this.passwords[0] == this.passwords[1]) {
      this.customer.accountPassword = this.passwords[0];
      this.passwordsNotMatched = false;
      this.formCount = 2;
    } else {
      this.passwordsNotMatched = true;
    }
  }

  setTPin(tPinForm) {
    this.transactionPins[0] = tPinForm.value.tPin1;
    this.transactionPins[1] = tPinForm.value.tPin2;
    if (this.transactionPins[0] == this.transactionPins[1]) {
      this.customer.transactionPin = this.transactionPins[0];
      this.passwordsNotMatched = false;
      console.log(this.customer);
      this._accountService.createAccount(this.customer).subscribe((customer) => {
        this.customer.accountId = customer.accountId;
        this.customer.balance = customer.balance;
        console.log(this.customer);
      }, (error) => {
        console.log(error);
      });
      setTimeout(() => {
        this._accountService.setCustomer(this.customer);
      this._accountService.setIsSignedIn(true);
      this._router.navigate(['/show/account']);
      }, 2000);
    }
  }

  editDetails () {
    this._accountService.updateAccount(this.customer).subscribe();
    this._router.navigate(['/show/account']);
  }

  editPassword(passwordForm) {
    this.wrongPassword = false;
    this.passwordsNotMatched = false;
    if (this.customer.accountPassword == passwordForm.value.password) {
      this.passwords[0] = passwordForm.value.password1;
      this.passwords[1] = passwordForm.value.password2;
      if (this.passwords[0] == this.passwords[1]) {
        this.customer.accountPassword = this.passwords[0];
        console.log(this.customer);
        this._accountService.updateAccount(this.customer).subscribe((customer) => {
          console.log(customer);
        }, (error) => {
          console.log(error);
        });
        setTimeout(() => {
          this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
            this._accountService.setCustomer(customer);
          });
          this._router.navigate(['/show/account']);
        }, 4000);
      } else {
        this.passwordsNotMatched = true;
      }
    } else {
      this.wrongPassword = true;
    }
  }

  editTPin(tPinForm) {
    this.wrongPassword = false;
    this.passwordsNotMatched = false;
    if (this.customer.transactionPin == tPinForm.value.tPin) {
      this.transactionPins[0] = tPinForm.value.tPin1;
      this.transactionPins[1] = tPinForm.value.tPin2;
      if (this.transactionPins[0] == this.transactionPins[1]) {
        this.customer.transactionPin = this.transactionPins[0];
        console.log(this.customer);
        this._accountService.updateAccount(this.customer).subscribe((customer) => {
          console.log(customer);
        }, (error) => {
          console.log(error);
        });
        setTimeout(() => {
          this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
            this._accountService.setCustomer(customer);
          });
          this._router.navigate(['/show/account']);
        }, 2000);
      } else {
        this.passwordsNotMatched = true;
      }
    } else {
      this.wrongPassword = true;
    }
  }

  checkNationality(nationalityProof) {
    if (nationalityProof == 'AadharCard') {
      this.nationality = 1;
      console.log(this.nationality);
    }
    if (nationalityProof == 'PanCard') {
      this.nationality = 2;
      console.log(this.nationality);
    }
  }
}
