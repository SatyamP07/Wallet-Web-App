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
  private passwordFlag: boolean;

  constructor(private _accountService: AccountService, private _router: Router) {
    this._router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };
  }

  ngOnInit() {
    this.isSignedIn = this._accountService.getIsSignedIn();
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
      this._accountService.createAccount(this.customer).subscribe((customer) => {
        this.customer.accountId = customer.accountId;
        this.customer.balance = customer.balance;
      });
      setTimeout(() => {
        this._accountService.setCustomer(this.customer);
      this._accountService.setIsSignedIn(true);
      alert('Account Created!');
      this._router.navigate(['/show/account']);
      }, 2000);
    }
  }

  editDetails () {
    setTimeout(() => {
    this._accountService.updateAccount(this.customer).subscribe(customer => this._accountService.setCustomer(customer));
    alert('Details Changed Successfully!');
    this._router.navigate(['/show/account']);
    }, 2000);
  }

  editPassword(passwordForm) {
    this.wrongPassword = false;
    this.passwordsNotMatched = false;
    this._accountService.checkAccountPassword(passwordForm.value.password, this.customer.accountId)
      .subscribe(flag => this.passwordFlag = true, err => this.passwordFlag = false);
    setTimeout(() => {
      if (this.passwordFlag) {
        this.passwords[0] = passwordForm.value.password1;
        this.passwords[1] = passwordForm.value.password2;
        if (this.passwords[0] == this.passwords[1]) {
          this.customer.accountPassword = this.passwords[0];
          this._accountService.updateAccount(this.customer).subscribe((customer) => {
          });
          setTimeout(() => {
            this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
              this._accountService.setCustomer(customer);
            });
            alert('Password Changed Successfully!');
            this._router.navigate(['/show/account']);
          }, 2000);
        } else {
          this.passwordsNotMatched = true;
        }
      } else {
        this.wrongPassword = true;
      }
    }, 2000);
  }

  editTPin(tPinForm) {
    this.wrongPassword = false;
    this.passwordsNotMatched = false;
    this._accountService.checkTransactionPin(tPinForm.value.tPin, this.customer.accountId)
      .subscribe(flag => this.passwordFlag = true, err => this.passwordFlag = false);
    setTimeout(() => {
      if (this.passwordFlag) {
        this.transactionPins[0] = tPinForm.value.tPin1;
        this.transactionPins[1] = tPinForm.value.tPin2;
        if (this.transactionPins[0] == this.transactionPins[1]) {
          this.customer.transactionPin = this.transactionPins[0];
          this._accountService.updateAccount(this.customer).subscribe();
          setTimeout(() => {
            this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
              this._accountService.setCustomer(customer);
            });
            alert('Password Changed Successfully!');
            this._router.navigate(['/show/account']);
          }, 2000);
        } else {
          this.passwordsNotMatched = true;
        }
      } else {
        this.wrongPassword = true;
      }
    }, 2000);
  }

  checkNationality(nationalityProof) {
    if (nationalityProof == 'AadharCard') {
      this.nationality = 1;
    }
    if (nationalityProof == 'PanCard') {
      this.nationality = 2;
    }
  }
}
