import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class ShareSheet extends React.Component {
  constructor(props) {
    super(props);
    this.state = {url: null};
    api.getSharedCartUrl((url) => {
      this.setState({url: url});
    });
  }
  render() {
    return (
      <div className='ShareSheet'>
        <h2>Share your Cart</h2>
        <p>Send this link to friends, and they'll be able to see which classes you've got in your cart. As you add or remove classes, they'll see the most up-to-date version.</p>
        <input onClick={(e) => e.target.setSelectionRange(0, e.target.value.length)} value={this.state.url || ''} />
      </div>
    )
  }
}
