import { CustomerDetails } from './../customer-details';
import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl: String = 'http://localhost:4444/XYZWallet';
  private headers = new Headers ({'Content-Type': 'application/json'});
  private options = {headers: this.headers};
  private customer: CustomerDetails;
  private createForm: boolean;
  private signedIn: boolean;
  private editType: number;
  private transacType: number;
  private showBalance: boolean;
  constructor(private _http: Http) { }

  getAccounts() {
    return this._http.get(this.baseUrl + '/accounts', this.options).pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  getAccount(id: Number) {
    return this._http.get(this.baseUrl + '/account/' + id, this.options).pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  deleteAccount(id: Number) {
    return this._http.delete(this.baseUrl + '/account/' + id, this.options).pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  createAccount(customer: CustomerDetails) {
    return this._http.post(this.baseUrl + '/account', JSON.stringify(customer), this.options)
      .pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  updateAccount(customer: CustomerDetails) {
    return this._http.put(this.baseUrl + '/account', JSON.stringify(customer), this.options)
      .pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  deposit(accountId: Number, money: Number) {
    return this._http.put(this.baseUrl + '/account/' + accountId + '/deposit',
                           money, this.options)
      .pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  withdraw(accountId: Number, money: Number) {
    return this._http.put(this.baseUrl + '/account/' + accountId + '/withdraw',
                          money, this.options)
      .pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  fundTransfer(accountId: Number, money: Number, receiverId: Number) {
    console.log([JSON.stringify(money), JSON.stringify(receiverId)], JSON.parse);
    return this._http.put(this.baseUrl + '/account/' + accountId + '/fundTransfer/' + receiverId + '/' + money,
                         [JSON.stringify(money), JSON.stringify(receiverId)], this.options)
      .pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  printTransactions(accountId: Number){
    return this._http.get(this.baseUrl + '/account/' + accountId + '/printTransactions', this.options)
      .pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  getSignedInAccount() {
    this.setIsSignedIn(true);
    return this._http.get(this.baseUrl + '/account/signedIn', this.options).pipe(map((response: Response) => response.json()))
    .pipe(catchError(this.errorHandler));
  }

  setCustomer(customer: CustomerDetails){
    this.customer = customer;
  }

  getCustomer(){
    return this.customer;
  }

  errorHandler(error: Response) {
    return Observable.throw(error || 'SERVER ERROR');
  }
  getIsSignedIn() {
    return this.signedIn;
  }
  setIsSignedIn(flag: boolean) {
    this.signedIn = flag;
  }

  setEditType(editType: number) {
    this.editType = editType;
  }
  getEditType() {
    return this.editType
  }

  setTransacType(type: number) {
    this.transacType = type;
  }
  getTransacType() {
    return this.transacType;
  }

  setShowBalance(showBalance: boolean) {
    this.showBalance = showBalance;
  }

  getShowBalance() {
    return this.showBalance;
  }
}

