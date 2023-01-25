
const balanceReducer = (state = 5000, action) => {
    // action type WITHDRAW or DEPOSIT
    switch(action.type) {
        // { type: 'WITHDRAW', payload: 2000 }
        case 'WITHDRAW': 
            return state - Number(action.payload);
            break;
        case 'DEPOSIT':
            return state + Number(action.payload);
            break;
        default:
            return state;
            break;
    }
}

export default balanceReducer;