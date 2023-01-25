import { combineReducers } from 'redux';
import balanceReducer from "./balanceReducer";
import transactionReducer from "./transactionReducer";
import medicineReducer from './medicineReducer';

const reducers = combineReducers({
    balance: balanceReducer,
    transaction: transactionReducer,
    medicine: medicineReducer
})

export default reducers;