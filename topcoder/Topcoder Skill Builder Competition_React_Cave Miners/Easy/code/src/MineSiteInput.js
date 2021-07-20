import React from 'react';
import {addMore, done} from './store/actions/button';
import {connect} from 'react-redux';

class MineSiteInput extends React.Component {

	render() {
		let orenum = this.props.orenum.orenum;
		//console.log("input:" + orenum);
		let inputData = [];
		for (let i = 0; i < orenum; ++i) {
			inputData.push("ore" + (i + 1));
		}
		const inputs = inputData.map((item, index) => 
			<input type="text" value="abc" id={item}/>
		);
		
		return (<div>
				<div>{inputs}</div>
				<div><button id="addMoreButton" onClick={() => {this.props.onAddClick(orenum)}}>Add More</button></div>
				<div><button id="doneButton" onClick={() => {this.props.onDoneClick(orenum);this.props.history.push('/overview',{state:orenum})}}>done</button></div>
			</div>
			);
	}
}

const mapStateToProps = state => {
	return {
		orenum: state.orenum
	}
}

const mapDispatchToProps = dispatch => {
	return {
		onAddClick: id => {
			dispatch(addMore(id));
		},

		onDoneClick: id => {
			dispatch(done(id));
		}
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(MineSiteInput);
