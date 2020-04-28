import { TransactionDetails } from './transaction-details';
export class CustomerDetails {
    accountId: number;
    accountPassword: String;
    name: String;
    eMail: String;
    mobileNumber: String;
    transactionPin: String;
    balance: number;
    nationalIdType: String;
    nationalIdNumber: String;
    transactionDetails: TransactionDetails[];
}
