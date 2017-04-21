import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class SectionInfo extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			inCart: this.props.inCart
		}
  	}

	render() {
		// Button to either add this section to the cart, or remove it
		let buttonTitle = this.state.inCart ? 'Remove from Cart' : 'Add to Cart';
	    let addRemoveButton = <input type='submit' value={ buttonTitle } onClick={this.addOrRemove.bind(this)}/>;

	    // Section info 
	    let sectionId = this.props.sectionId;

		return (
			<div className="section">
				<h4 className="sectionName">{sectionId}</h4> 
				put time here {addRemoveButton}
			</div>
		)	
	}

	addOrRemove(e) {
		if (this.state.inCart) {
			api.removeFromCart(this.props.sectionId, this.props.onAdd);
			this.setState({inCart: false});
		}	else {
			api.addToCart(this.props.sectionId, this.props.onRemove);
			this.setState({inCart: true});
		}
	}

}
