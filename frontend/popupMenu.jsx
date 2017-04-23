import React from 'react';
import ReactDOM from 'react-dom';

export default class PopupMenu extends React.Component {
  constructor(props) {
    super(props);
    this.state = {open: false};
  }
  render() {
    return (
      <div className='PopupMenuSource'>
        <div onClick={this.open.bind(this)}>{this.props.children}</div>
        {this.renderMenu()}
      </div>
    )
  }
  renderMenu() {
    if (this.state.open) {
      return <div className='PopupMenu'>{this.props.menu}</div>;
    } else {
      return null;
    }
  }
  close() {
    console.log('close')
    setTimeout(() => this.setState({open: false}), 100);
  }
  open() {
    console.log('open')
    if (!this.state.open) {
      this.setState({open: true});
    }
  }
  componentDidMount() {
    // install global click handler to dismiss this menu
    let onClick = () => {
      if (this.state.open) {
        console.log('global click')
        this.close();
      }
    };
    $(document.body).click(onClick);
    this.globalOnClick = onClick;
  }
  componentWillUnmount() {
    if (this.globalOnClick) {
      $(document.body).off('click', this.globalOnClick);
      delete this.globalOnClick;
    }
  }
}
