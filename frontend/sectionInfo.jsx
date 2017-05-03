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
	    let style = this.getStyle();
	    let times = descriptionForSectionTimes(this.props.times);
	    let professors = this.props.professors.join(', ');
	    let locations = this.getLocationString(this.props.locations);
	    let labelStyle = this.getLabelStyle();

		// Button to either add this section to the cart, or remove it
		let buttonTitle = this.state.inCart ? 'Remove' : 'Add';
	    // let addRemoveButton = <input type='submit' value={ buttonTitle } onClick={this.addOrRemove.bind(this)}/>;
	    let addRemoveButton = times ? <div className="addRemove" onClick={this.addOrRemove.bind(this)}><label style={labelStyle}>{buttonTitle}</label></div> : null
      if (this.props.locked) {
        addRemoveButton = null;
      }

		return (
			<div className="sectionInfo" style={style}>
				<h4 className="sectionName" style={labelStyle}>{sectionId}</h4> 
				<p style={labelStyle}>{locations}</p>
				<p style={labelStyle}>{times}</p>
				<p style={labelStyle}>{professors}</p>
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

	/*
		Formatted location string
	*/
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

	/*
		Styling for sections already in cart
	*/
	getStyle = () => {
		if (this.state.inCart) {
			return (
			{
				backgroundImage: 'linear-gradient(135deg, rgb(122, 91, 206) 0%, rgb(71, 117, 202) 100%)',
				borderRadius: '3px',
				color: 'white'
			}
				
			);
		} else {
			return {};
		}
	}

	getLabelStyle = () => {
		if (this.state.inCart) {
			return (
			{
				color: 'white',
				marginLeft: '8px',
				borderColor: 'rgba(255,255,255,1)'
			}
				
			);
		} else {
			return {
				marginLeft: '8px'
			};
		}
	}
}
