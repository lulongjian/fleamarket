export default {
	handleVal(state, payload) {
		state.userName = payload
	},
	decrementF(state) {
		state.flag = false
	},
	decrementT(state) {
		state.flag = true
	}
}