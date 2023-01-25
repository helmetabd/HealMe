const transactionReducer = (state = [], action) => {
    console.log('action type' + action.type)
    console.log(action.payload)
    switch(action.type) {
        case 'ADD_TRANSACTION': 
            return [ ...state, action.payload ]
            break;
        default:
            return state;
            break;
    }
}

export default transactionReducer;