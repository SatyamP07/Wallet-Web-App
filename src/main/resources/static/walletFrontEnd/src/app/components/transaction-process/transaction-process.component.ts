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
  private customers: CustomerDetails[];
  private transactionType: number;
  private transactionFlag: boolean;
  private tpinFlag: boolean;
  private money: number;
  private wrongPassword: boolean;
  private lowBalance: boolean;
  private negativeAmount: boolean;
  private receiverId: number;
  private receiverExists: boolean;
  private invalidAccountId: boolean;
  private success: boolean = false;

  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this.customer = this._accountService.getCustomer();
    this.transactionType = this._accountService.getTransacType();
    this._accountService.getAccounts().subscribe(customers => this.customers = customers);
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
    this.wrongPassword = false;
    this._accountService.checkTransactionPin(depositForm.value.tPin, this.customer.accountId)
      .subscribe(flag => this.tpinFlag = true, err => this.tpinFlag = false);
    setTimeout(() => {
      if (this.tpinFlag) {
        this._accountService.deposit(this.customer.accountId, this.money).subscribe();
        this.success = true;
        setTimeout(() => {
          this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
            this._accountService.setCustomer(customer);
            this._router.navigate(['/show/account']);
          });
        }, 2000);
      } else {
        this.wrongPassword = true;
      }
    }, 2000);
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
    this.wrongPassword = false;
    this._accountService.checkTransactionPin(withdrawForm.value.tPin, this.customer.accountId)
      .subscribe(flag => this.tpinFlag = true, err => this.tpinFlag = false);
    setTimeout(() => {
      if (this.tpinFlag) {
        this._accountService.withdraw(this.customer.accountId, this.money).subscribe();
        this.success = true;
        setTimeout(() => {
          this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
            this._accountService.setCustomer(customer);
            this._router.navigate(['/show/account']);
          });
        }, 2000);
      } else {
        this.wrongPassword = true;
      }
    }, 2000);
  }

  proceedFundTransfer(fundTransferForm) {
    this.receiverId = fundTransferForm.value.receiverId;
    this.money = fundTransferForm.value.money;
    for (let customer of this.customers) {
      if (customer.accountId == this.receiverId) {
        this.receiverExists = true;
        this.invalidAccountId = false;
        break;
      }
    }
    if (this.receiverExists && this.receiverId != this.customer.accountId) {
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
    } else {
      this.invalidAccountId = true;
    }
  }

  fundTransfer(fundTransferForm) {
    this.wrongPassword = false;
    this._accountService.checkTransactionPin(fundTransferForm.value.tPin, this.customer.accountId)
      .subscribe(flag => this.tpinFlag = true, err => this.tpinFlag = false);
    setTimeout(() => {
      if (this.tpinFlag) {
        this._accountService.fundTransfer(this.customer.accountId, this.money, this.receiverId).subscribe();
        this.success = true;
        setTimeout(() => {
          this._accountService.getAccount(this.customer.accountId).subscribe((customer) => {
            this._accountService.setCustomer(customer);
            this._router.navigate(['/show/account']);
          });
        }, 2000);
      } else {
        this.wrongPassword = true;
      }
    }, 2000);
  }
}
