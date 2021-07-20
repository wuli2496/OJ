import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route} from "react-router-dom";

import MineSiteInput from './MineSiteInput';
import MineSiteOverview  from './MineSiteOverview';


function App() {
  return (
    	<div className="App">
      		<header className="App-header">
        	<img src={logo} className="App-logo" alt="logo" />
        	<p>
          	Edit <code>src/App.js</code> and save to reload.
        	</p>
        	<a
          		className="App-link"
          		href="https://reactjs.org"
          		target="_blank"
          		rel="noopener noreferrer"
       	 	>
          		Learn React
        	</a>
		<Router>
			<Route component={MineSiteInput} exact path="/"/>
			<Route component={MineSiteOverview} exact path="/overview"/>
		</Router>
      		</header>
    	</div>
  );
}

export default App;
