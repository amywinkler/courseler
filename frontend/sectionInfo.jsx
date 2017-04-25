import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { descriptionForSectionTimes } from './timeFormatter.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';

export default class SectionInfo extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			inCart: this.props.inCart,
			sectionTimes: ''
		}
  	}

	render() {

	    // Section info 
	    let sectionId = this.props.sectionId;
	    let style = {backgroundColor: 'white'};
	    let times = descriptionForSectionTimes(this.props.times);
	    let professors = this.props.professors;
	    let locations = this.getLocationString(this.props.locations);

		// Button to either add this section to the cart, or remove it
		let buttonTitle = this.state.inCart ? 'Remove' : 'Add to Cart';
	    // let addRemoveButton = <input type='submit' value={ buttonTitle } onClick={this.addOrRemove.bind(this)}/>;
	    let addRemoveButton = <div className="addRemove" onClick={this.addOrRemove.bind(this)}>{buttonTitle}</div>	

		return (
			<div className="sectionInfo" style={style}>
				<h4 className="sectionName">{sectionId}</h4> 
				<p>{locations}</p>
				<p>{times}</p>
				<p>{professors}</p>
				{addRemoveButton}
			</div>
		)	
	}

	addOrRemove(e) {
		if (this.state.inCart) {
			api.removeFromCart(this.props.sectionId, this.props.onAdd);
			this.setState({inCart: false});
			navigateToRoute({});
		}	else {
			api.addToCart(this.props.sectionId, this.props.onRemove);
			this.setState({inCart: true});
			navigateToRoute({});
		}
	}

	getLocationString(locations){
		let weekdays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday'];
		let locationArray = new Set();
		weekdays.forEach((weekday) => {
	    	let loc = locations[weekday + 'MeetingLoc'];
		    if (loc !== null) {
	      		locationArray.add(loc);
	    	};
    	});
    	return Array.from(locationArray).join(', ');
	}


}
