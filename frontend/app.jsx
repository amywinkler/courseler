import React from 'react';
import ReactDOM from 'react-dom';
import Secret from './secret.jsx';
import LoginScreen from ....
import API from './fakeapi.jsx';

class App extends React.Component {
  constructor(props) {
     super(props);
     this.state = {count: 0};
   }
  render() {
    let messages = [];
    for (let i=0; i<this.state.count; i++) {
      messages.push(<Message />);
    }
    return (
    if (this.state.isLoggedIn) {
      return <Calendar />;
    } else {
      return <LoginScreen />;
    }
    <div>
        hey! this has been clicked {this.state.count} times!
        {messages}
        <Secret />
        <button onClick={ () => {this.clicked()} }>click</button>
    </div>);
  }
  clicked() {
    this.setState({count: this.state.count + 1});
  }
}

ReactDOM.render(
  <App />,
  document.getElementById('app')
);
