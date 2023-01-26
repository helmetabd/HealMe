const hospitalReducer = (state = {}, action) => {
    switch (action.type) {
        case 'SET_HOSPITAL':
            return action.payload;
            break;
        case 'DELETE_VEHICLE':
            return state.filter(car => {
                if (car._id != action.payload.id) {
                    return state
                }
            })
            break;
        default:
            return state;
            break;
    }
}

export default hospitalReducer;