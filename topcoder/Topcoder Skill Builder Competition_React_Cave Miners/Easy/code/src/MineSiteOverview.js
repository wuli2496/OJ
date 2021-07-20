import React from 'react';
import {connect} from 'react-redux';
import store from './store/store';

class MineSiteOverview extends React.Component {

	render() {
		let s = store.getState();
		Object.keys(s).forEach(function(key){
			//console.log("Overview:" + key, s[key]);
		});
		let num = 1;
		console.log("storage orenum:" + localStorage.getItem("orenum"));
		if (localStorage.getItem("orenum")) {
			num = localStorage.getItem("orenum");
		} else {
			num = this.props.orenum.orenum;
		}
		let inputData = [];
		for (let i = 0; i < num; ++i) {
			inputData.push(i);
		}

		const data = inputData.map((item, index) =>
			<li key={index}>ore{index + 1}</li>);
		return <div>Overview<ul>{data}</ul></div>
	}
}

const mapStateToProps = state => {
	return {
		orenum: state.orenum
	}
}

const mapDispatchToProps = dispatch => {
	return {}
}
export default connect(mapStateToProps, mapDispatchToProps)(MineSiteOverview);
