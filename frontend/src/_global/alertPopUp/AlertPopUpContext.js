import React, { useState, createContext, useReducer } from 'react';

const initialState = {
    alertPopUps: []
};


const ADD_ALERT_POP_UP = "ADD_ALERT_POP_UP";
const DELETE_ALERT_POP_UP = "DELETE_ALERT_POP_UP";

const reducer = (state, action) => {
    switch (action.type) {
        case ADD_ALERT_POP_UP:
            return {
                ...state,
                alertPopUps: [...state.alertPopUps, action.payload]
            };
        
        case DELETE_ALERT_POP_UP:
            return {
                ...state,
                alertPopUps: state.alertPopUps.filter((alertPopUp) => 
                    alertPopUp.id !== action.payload
                )
            };

        default :
            return state;
    }
}


const AlertPopupContext = createContext();

const AlertPopupProvider = ({ children }) => {
    const [alertPopupState, dispatch] = useReducer(reducer, initialState);

    const [addAlertPopUp] = useState(() => {
        return (message, type) => {
            const alertId = Date.now()
            setTimeout(() => {
                dispatch({type: DELETE_ALERT_POP_UP, payload: alertId})
            }, 5000);
        
            dispatch({type: ADD_ALERT_POP_UP, payload: {message: message, type:type, id:alertId}})
        };
    });

    const [deleteAlertPopUp] = useState(() => {
        return (id) => {
                dispatch({type: DELETE_ALERT_POP_UP, payload: id})
            };
    });

    return (
        <AlertPopupContext.Provider value={{ alertPopupState, addAlertPopUp, deleteAlertPopUp }}>
            {children}
        </AlertPopupContext.Provider>
    )
}

export { AlertPopupContext, AlertPopupProvider };