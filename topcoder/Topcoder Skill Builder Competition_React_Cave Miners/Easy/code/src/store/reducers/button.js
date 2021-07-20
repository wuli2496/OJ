const buttonReducer = (state = {orenum:1}, action) =>  {
	console.log(action);
	switch (action.type) {
		case 'ADD_MORE_BUTTON':
		     let tmp = action.id + 1;
		     //console.log("tmp:" + tmp);
			return Object.assign({}, state, {orenum:tmp});
		case 'DONE':
			window.localStorage.setItem("orenum", action.id);
			//console.log("DONE");
			return Object.assign({}, state, {orenum:action.id});
		default:
			return Object.assign({}, state);
	}
}

export default buttonReducer;
