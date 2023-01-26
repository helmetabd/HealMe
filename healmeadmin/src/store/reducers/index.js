import { combineReducers } from 'redux';
import balanceReducer from "./balanceReducer";
import transactionReducer from "./transactionReducer";
import medicineReducer from './medicineReducer';
import hospitalReducer from './hospitalReducer';

const reducers = combineReducers({
    balance: balanceReducer,
    transaction: transactionReducer,
    hospital: hospitalReducer,
    medicine: medicineReducer
})

export default reducers;