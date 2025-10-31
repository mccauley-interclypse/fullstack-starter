import { createAction, handleActions } from 'redux-actions'

const actions = {
  INVENTORY_GET_ALL: 'inventory/get_all',
  INVENTORY_GET_ALL_PENDING: 'inventory/get_all_PENDING',
  INVENTORY_SAVE: 'inventory/save',
  INVENTORY_DELETE: 'inventory/delete',
  INVENTORY_REFRESH: 'inventory/refresh'
}

export let defaultState = {
  all: []
}

export const findInventory = createAction(actions.INVENTORY_GET_ALL, () => {
  (dispatch, getState, config) => Axios
    .get(`${config.restAPIUrl}/inventory`)
    .then((suc) => dispatch(refreshInventory(suc.data)))
})

export default handleActions({
  //TODO
}, defaultState)
