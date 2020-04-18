import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';

import { ShowAccountsComponent } from './components/show-accounts/show-accounts.component';
import { TransactionProcessComponent } from './components/transaction-process/transaction-process.component';
import { AccountFormComponent } from './components/account-form/account-form.component';
import { AccountService } from './service/account.service';
import { ShowAccountComponent } from './components/show-account/show-account.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';

const appRoute: Routes = [
    {path: '', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'admin' , component: ShowAccountsComponent},
    {path: 'tp' , component: TransactionProcessComponent},
    {path: 'form' , component: AccountFormComponent},
    {path: 'show' , component: ShowAccountComponent}
];

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        FormsModule,
        RouterModule.forRoot(appRoute)
    ],
    declarations: [
        AppComponent,
        ShowAccountsComponent ,
        TransactionProcessComponent ,
        AccountFormComponent,
        ShowAccountComponent,
        ShowAccountComponent ,
        HomeComponent
,
        LoginComponent    ],
    providers: [AccountService],
    bootstrap: [AppComponent]
})

export class AppModule { }
