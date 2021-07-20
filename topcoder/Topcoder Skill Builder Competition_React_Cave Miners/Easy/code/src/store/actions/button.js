
export const addMore = ordId => {
	return {
		type:'ADD_MORE_BUTTON',
		id: ordId++
	}
}

export const done = id => {
	return {
		type:'DONE',
		id
	}
}
