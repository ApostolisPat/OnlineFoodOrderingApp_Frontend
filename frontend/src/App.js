import logo from './logo.svg'
import './App.css';
import { Navbar } from './component/NavBar/Navbar/Navbar';
import { darkTheme } from './theme/DarkTheme';
import { CssBaseline, ThemeProvider } from '@mui/material';
import Home from './component/Home/Home';

function App() {
  return (

    <ThemeProvider theme={darkTheme}>
        
      <CssBaseline/>
        <Navbar/>
      <Home/>

    </ThemeProvider>

  );
}

export default App;
