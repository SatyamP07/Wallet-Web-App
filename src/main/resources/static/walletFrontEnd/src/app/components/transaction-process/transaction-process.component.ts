import { Router } from '@angular/router';
import { AccountService } from './../../services/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-transaction-process',
  templateUrl: './transaction-process.component.html',
  styleUrls: ['./transaction-process.component.css']
})
export class TransactionProcessComponent implements OnInit {
  private customer: CustomerDetails;
  private transactionType: number;
  private transactionFlag: boolean;
  private money: number;
  private wrongPassword: boolean;
  private lowBalance: boolean;
  private negativeAmount: boolean;
  private receiverId: number;

  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this.customer = this._accountService.getCustomer();
    this.transactionType = this._accountService.getTransacType();
  }

  proceedDeposit (depositForm) {
    this.money = depositForm.value.money;
    this.negativeAmount = false;
    if (this.money > 0) {
      this.transactionFlag = true;
    } else {
      this.negativeAmount = true;
    }
  }

  deposit(depositForm) {
    if (this.customer.transactionPin == depositForm.value.tPin) {
      this._accountService.deposit(this.customer.accountId, this.money).subscribe();
      setTimeout(() => {
        this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
          this._accountService.setCustomer(customer);
        });
      }, 4000);
      this._router.navigate(['/show/account']);
    } else {
      this.wrongPassword = true;
    }
  }

  proceedWithdraw(withdrawForm) {
    this.lowBalance = false;
    this.money = withdrawForm.value.money;
    if (this.customer.balance > this.money) {
        this.negativeAmount = false;
      if (this.money > 0) {
        this.transactionFlag = true;
      } else {
        this.negativeAmount = true;
      }
    } else {
      this.lowBalance = true;
    }
  }

  withdraw(withdrawForm) {
    if (this.customer.transactionPin == withdrawForm.value.tPin) {
      this._accountService.withdraw(this.customer.accountId, this.money).subscribe();
      setTimeout(() => {
        this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
          this._accountService.setCustomer(customer);
        });
      }, 4000);
      this._router.navigate(['/show/account']);
    } else {
      this.wrongPassword = true;
    }
  }

  proceedFundTransfer(fundTransferForm) {
    this.receiverId = fundTransferForm.value.receiverId;
    this.money = fundTransferForm.value.money;
      if (this.customer.balance > this.money) {
        this.negativeAmount = false;
      if (this.money > 0) {
        this.transactionFlag = true;
      } else {
        this.negativeAmount = true;
      }
    } else {
      this.lowBalance = true;
    }
  }

  fundTransfer(fundTransferForm) {
    if (this.customer.transactionPin == fundTransferForm.value.tPin) {
      this._accountService.fundTransfer(this.customer.accountId, this.money, this.receiverId).subscribe();
      setTimeout(() => {
        this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
          this._accountService.setCustomer(customer);
        });
      }, 4000);
      this._router.navigate(['/show/account']);
    } else {
      this.wrongPassword = true;
    }
  }
}
