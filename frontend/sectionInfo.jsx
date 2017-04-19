import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class SectionInfo extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			//maybe check whether its in the cart by passing the cart to courseinfo??
			inCart: true
		}
  }

	render() {
		// add or remove to cart button
		let buttonTitle = this.state.inCart ? 'Remove from Cart' : 'Add to Cart';
    let addRemoveButton = <input type='submit' value={ buttonTitle } onClick={this.addOrRemove.bind(this)}/>;

    // section info
    let sectionId = this.props.sectionId;

		return (
			<div> <h4>{sectionId}</h4> put time here {addRemoveButton} </div>
		)	
	}

	addOrRemove(e) {
		if (this.state.inCart) {
			api.removeFromCart(this.props.sectionId, console.log);
			this.setState({inCart: false});
		}	else {

		}
	}

}
