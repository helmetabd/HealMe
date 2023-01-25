const medicineReducer = (state = {}, action) => {
    switch (action.type) {
        case 'SET_MEDICINE':
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

export default medicineReducer;