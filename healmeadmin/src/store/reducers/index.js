import { combineReducers } from 'redux';
import medicineReducer from './medicineReducer';
import hospitalReducer from './hospitalReducer';

const reducers = combineReducers({
    hospital: hospitalReducer,
    medicine: medicineReducer
})

export default reducers;